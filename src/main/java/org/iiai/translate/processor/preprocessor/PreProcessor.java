package org.iiai.translate.processor.preprocessor;

import org.iiai.translate.model.Document;

public interface PreProcessor {
    void process(Document document, String modelId);
}
