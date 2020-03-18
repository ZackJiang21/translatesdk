package org.iiai.translate.processor;

public interface PostProcessor {
    String process(String input, String modelId);
}
