package org.iiai.translate.processor.mergeprocessor;

public class PuncProcessor implements MergeProcessor {
    @Override
    public String process(String input, String modelId) {
        input = input.replaceAll("( +)([!#%&\\'\\)\\]*+,./:;<=>\\-?@^_`{|}~،؟])", "$2");
        input = input.replaceAll("([&*+\\-/<=>@\\[^_`\\(\\'{|}~$])( +)", "$1");
        return input;
    }
}
