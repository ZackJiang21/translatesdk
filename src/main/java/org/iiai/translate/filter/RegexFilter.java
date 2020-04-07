package org.iiai.translate.filter;

import org.iiai.translate.model.Separator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexFilter implements SeparatorFilter {

    private Pattern pattern;

    public RegexFilter(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    @Override
    public List<Separator> getSeparators(String sentence) {
        List<Separator> separatorList = new ArrayList<>();
        Matcher matcher = pattern.matcher(sentence);
        while (matcher.find()) {
            Separator separator = new Separator(matcher.start(), matcher.end(), matcher.group());
            separatorList.add(separator);
        }
        return separatorList;
    }
}
