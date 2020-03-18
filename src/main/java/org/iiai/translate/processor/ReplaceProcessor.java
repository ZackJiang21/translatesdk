package org.iiai.translate.processor;

import org.iiai.translate.constant.TranslateConst;

public class ReplaceProcessor implements PostProcessor {
    @Override
    public String process(String input, String modelId) {
        if (TranslateConst.AR2EN_ID.equals(modelId)) {
            input = input.replace("gon na", "gonna");
        }
        return input;
    }
}
