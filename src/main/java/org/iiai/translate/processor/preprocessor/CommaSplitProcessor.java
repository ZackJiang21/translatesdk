package org.iiai.translate.processor.preprocessor;

import org.iiai.translate.constant.SpecSymbolType;
import org.iiai.translate.constant.TranslateConst;
import org.iiai.translate.model.Document;
import org.iiai.translate.model.Sentence;
import org.iiai.translate.model.type.SingleSentType;
import org.iiai.translate.model.type.SpecSymbol;
import org.iiai.translate.util.SentenceUtil;

import java.util.*;

public class CommaSplitProcessor implements PreProcessor {
    private static final Map<String, String> commaSplitMap = new HashMap<>();

    private static final Map<String, String> commaResultMap = new HashMap<>();

    private static final int MAX_COMMA_COUNT = 4;

    private static final int SHORT_WORD_COUNT = 4;

    private static final double LIST_THRESHOLD = 0.8;

    private static final int COMMA_THRESHOLD = 25;

    static {
        commaSplitMap.put(TranslateConst.AR2EN_ID, "،");
        commaSplitMap.put(TranslateConst.EN2AR_ID, ",");
        commaResultMap.put(TranslateConst.AR2EN_ID, ",");
        commaResultMap.put(TranslateConst.EN2AR_ID, "،");
    }

    @Override
    public void process(Document document, String modelId) {
        List<Sentence> retList = new ArrayList<>();
        for (Sentence sentObj : document.getSentenceList()) {
            if (sentObj.getType() instanceof SpecSymbol) {
                retList.add(sentObj);
            } else {
                processSplit(sentObj, retList, modelId);
            }
        }
        document.setSentenceList(retList);
    }

    private void processSplit(Sentence sentObj, List<Sentence> sentenceList, String modelId) {
        String commaSplit = commaSplitMap.get(modelId);
        if (sentObj.getSentContent().contains(commaSplit)) {
            if (isList(sentObj, modelId)) {
                handleList(sentObj, modelId, sentenceList);
            } else {
                handleNotList(sentObj.getSentContent(), modelId, sentenceList, false, true);
            }
        } else {
            sentenceList.add(sentObj);
        }
    }

    private boolean isList(Sentence sentObj, String modelId) {
        String commaSplit = commaSplitMap.get(modelId);
        List<String> commaSentList = Arrays.asList(sentObj.getSentContent().split(commaSplit));
        if (commaSentList.size() > MAX_COMMA_COUNT) {
            int shortWordCnt = 0;
            for (String commaSent : commaSentList) {
                if (commaSent.split(" ").length < SHORT_WORD_COUNT) {
                    shortWordCnt++;
                }
            }
            if (((double) shortWordCnt / commaSentList.size()) < LIST_THRESHOLD) {
                return true;
            }
        }
        return false;
    }

    private void handleList(Sentence sentObj, String modelId, List<Sentence> sentenceList) {
        String commaSplit = commaSplitMap.get(modelId);
        String commaResult = commaResultMap.get(modelId);
        List<String> commaSentList = Arrays.asList(sentObj.getSentContent().split(commaSplit));
        for (int i = 0; i < commaSentList.size(); i++) {
            if (i != commaSentList.size() - 1) {
                boolean isLower = i == 0 ? false : true;
                SingleSentType sentenceType = new SingleSentType(true, isLower);
                sentenceList.add(new Sentence(commaSentList.get(i).trim(), sentenceType));
                sentenceList.add(new Sentence(commaResult, new SpecSymbol(SpecSymbolType.COMMA)));
            } else {
                SingleSentType sentenceType = new SingleSentType(false, true);
                sentenceList.add(new Sentence(commaSentList.get(i).trim(), sentenceType));
            }
        }
    }

    private void handleNotList(String sentence, String modelId, List<Sentence> sentenceList, boolean isFront, boolean isFirst) {
        String commaSplit = commaSplitMap.get(modelId);
        List<Integer> commaIndexList = SentenceUtil.getIndexList(sentence, commaSplit);

        if (isCanSplit(commaIndexList, sentence)) {
            int commaIndex = commaIndexList.size() / 2;
            int splitIndex = commaIndexList.get(commaIndex);
            String frontSent = sentence.substring(0, splitIndex);
            String rearSent = sentence.substring(splitIndex + 1);
            handleNotList(frontSent, modelId, sentenceList, true, isFirst);
            handleNotList(rearSent, modelId, sentenceList, false, false);
        } else {
            boolean isLower = (isFirst && isFront) ? false : true;
            boolean isNonStop = isFront ? true : false;
            SingleSentType sentType = new SingleSentType(isNonStop, isLower);
            sentenceList.add(new Sentence(sentence.trim(), sentType));
            if (isFront) {
                String commaResult = commaResultMap.get(modelId);
                sentenceList.add(new Sentence(commaResult, new SpecSymbol(SpecSymbolType.COMMA)));
            }
        }
    }


    private boolean isCanSplit(List<Integer> indexList, String sentence) {
        if (indexList.isEmpty()) {
            return false;
        }
        if (sentence.split(" ").length <= COMMA_THRESHOLD) {
            return false;
        }
        return true;
    }

}
