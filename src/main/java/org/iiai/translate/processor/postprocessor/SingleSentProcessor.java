package org.iiai.translate.processor.postprocessor;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.iiai.translate.constant.TranslateConst;
import org.iiai.translate.model.Document;
import org.iiai.translate.model.Sentence;
import org.iiai.translate.model.type.SingleSentType;
import org.iiai.translate.util.SentenceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SingleSentProcessor implements PostProcessor {
    private static final Set<Character> STOP_CHAR_SET = Sets.newHashSet('.', '!', '?', ',', '،', '؟');

    @Override
    public void process(Document document, String modelId) {
        List<String> processTransList = new ArrayList<>();

        List<Sentence> wordSentList = document.getSentenceByType(SingleSentType.class);
        List<String> transList = document.getTransList();

        for (int i = 0; i < wordSentList.size(); i++) {
            Sentence sentObj = wordSentList.get(i);
            SingleSentType sentType = (SingleSentType) sentObj.getType();
            String translation = transList.get(i).trim();
            translation = processLower(sentType, translation, modelId);
            translation = processNonStop(sentType, translation);
            processTransList.add(translation);
        }
        document.setTransList(processTransList);
    }

    private String processNonStop(SingleSentType sentType, String translation) {
        if (sentType.isNonStop()) {
            char lastChar = translation.charAt(translation.length() - 1);
            if (STOP_CHAR_SET.contains(lastChar)) {
                translation = translation.substring(0, translation.length() - 1);
            }
        }
        return translation;
    }

    private String processLower(SingleSentType sentType, String translation, String modelId) {
        // Arabic do not have lower case
        if (TranslateConst.AR2EN_ID.equals(modelId) && sentType.isLower()) {
            String firstWord = translation.split(" ")[0];
            if (firstWord.length() == 1 && Character.isUpperCase(firstWord.charAt(0))) {
                translation = SentenceUtil.lowerFirstLetter(translation);
            } else if (firstWord.length() > 1 && Character.isUpperCase(firstWord.charAt(0))
            && StringUtils.isAllLowerCase(firstWord.substring(1))) {
                translation = SentenceUtil.lowerFirstLetter(translation);
            }
        }
        return translation;
    }
}
