package org.iiai.translate.processor.postprocessor;

import org.iiai.translate.model.Document;

public interface PostProcessor {
    void process(Document document, String modelId);
}
