package org.iiai.translate.processor.mergeprocessor;

import org.apache.commons.text.StringEscapeUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlProcessor implements MergeProcessor {
    @Override
    public String process(String input, String modelId) {
        Pattern pattern = Pattern.compile("& *(\\w+) *;");
        Matcher matcher = pattern.matcher(input);

        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, decodeHtml(matcher.group(1)));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private String decodeHtml(String input) {
        if (input.length() > 5) {
            return input;
        } else {
            input = input.toLowerCase();
            input = StringEscapeUtils.unescapeHtml4("&" + input + ";");
            return StringEscapeUtils.unescapeXml(input);
        }
    }

}
