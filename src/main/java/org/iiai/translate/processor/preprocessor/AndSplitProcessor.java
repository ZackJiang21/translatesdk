package org.iiai.translate.processor.preprocessor;

import org.apache.commons.lang3.StringUtils;
import org.iiai.translate.constant.TranslateConst;
import org.iiai.translate.model.Document;
import org.iiai.translate.model.Sentence;
import org.iiai.translate.model.type.SingleSentType;
import org.iiai.translate.model.type.SpecSymbol;
import org.iiai.translate.util.SentenceUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AndSplitProcessor implements PreProcessor {
    private static final String AND_MARK = "و";

    private static final int AND_SENT_THRESHOLD = 25;

    private static final int AND_SENT_BOTTOM = 3;

    @Override
    public void process(Document document, String modelId) {
        if (TranslateConst.AR2EN_ID.equals(modelId)) {
            List<Sentence> retList = new ArrayList<>();
            for (Sentence sentObj : document.getSentenceList()) {
                if (sentObj.getType() instanceof SpecSymbol) {
                    retList.add(sentObj);
                } else {
                    List<String> wordList = Arrays.asList(sentObj.getSentContent().split(" "));
                    SingleSentType sentType = (SingleSentType) sentObj.getType();
                    handleSplit(sentType, wordList, retList, false, true);
                }
            }
            document.setSentenceList(retList);
        }
    }

    private void handleSplit(SingleSentType sentType, List<String> wordList, List<Sentence> sentenceList, boolean isFront, boolean isFirst) {
        List<Integer> andIndexList = SentenceUtil.getIndexList(wordList, AND_MARK);
        if (isCanSplit(andIndexList, wordList)) {
            int splitIndex = andIndexList.get(andIndexList.size() / 2);
            List<String> frontWordList = wordList.subList(0, splitIndex);
            List<String> rearWordList = wordList.subList(splitIndex, wordList.size());
            handleSplit(sentType, frontWordList, sentenceList, true, isFirst);
            handleSplit(sentType, rearWordList, sentenceList, false, false);
        } else {
            boolean isNonStop = (isFront ? true : false) || sentType.isNonStop();
            boolean isLower = (isFirst ? false : true) || sentType.isLower();

            SingleSentType retType = new SingleSentType(isNonStop, isLower);
            String sentence = String.join(" ", wordList);
            if (StringUtils.isNotEmpty(sentence.trim())) {
                sentenceList.add(new Sentence(sentence.trim(), retType));
            }
        }
    }

    private boolean isCanSplit(List<Integer> indexList, List<String> wordList) {
        if (indexList.isEmpty()) {
            return false;
        }
        if (indexList.size() == 1 && indexList.get(0) == 0) {
            return false;
        }
        int splitIndex = indexList.get(indexList.size() / 2);
        if (wordList.subList(0, splitIndex).size() < AND_SENT_BOTTOM
                || wordList.subList(splitIndex, wordList.size()).size() < AND_SENT_BOTTOM) {
            return false;
        }
        if (wordList.size() <= AND_SENT_THRESHOLD) {
            return false;
        }
        return true;
    }
}
