package org.iiai.translate.processor.mergeprocessor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatProcessor implements MergeProcessor {
    @Override
    public String process(String input, String modelId) {
        Pattern pattern = Pattern.compile("\\s*\n\\s+");
        Matcher matcher = pattern.matcher(input);

        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
           matcher.appendReplacement(sb, formatNewLine(matcher.group()));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private String formatNewLine(String input) {
        StringBuffer sb = new StringBuffer();
        for (char c : input.toCharArray()) {
            if (c == '\n') {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
