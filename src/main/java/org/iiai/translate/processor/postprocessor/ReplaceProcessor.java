package org.iiai.translate.processor.postprocessor;

import org.iiai.translate.constant.TranslateConst;
import org.iiai.translate.model.Document;

import java.util.ArrayList;
import java.util.List;

public class ReplaceProcessor implements PostProcessor {
    @Override
    public void process(Document document, String modelId) {
        if (TranslateConst.AR2EN_ID.equals(modelId)) {
            List<String> transList = new ArrayList<>();
            for (String translation : document.getTransList()){
                translation = translation.replace("gon na", "gonna");
                transList.add(translation);
            }
            document.setTransList(transList);
        }
    }
}
