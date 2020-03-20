package org.iiai.translate.processor.postprocessor;

public interface PostProcessor {
    String process(String input, String modelId);
}
