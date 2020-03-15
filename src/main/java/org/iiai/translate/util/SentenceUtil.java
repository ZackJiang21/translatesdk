package org.iiai.translate.util;

import org.iiai.translate.model.Separator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SentenceUtil {
    private SentenceUtil() {

    }

    public static List<Separator> mergeSeparator(List<Separator> separators, String sentence) {
        Collections.sort(separators, Comparator.comparingInt(Separator::getStart));
        List<Separator> mergeResult = new ArrayList<>();

        int index = 0;
        while (index < separators.size()) {
            Separator first = separators.get(index);
            for (int j = index + 1; j < separators.size(); j++) {
                Separator second = separators.get(j);
                if (second.getStart() <= first.getEnd() && first.getEnd() < second.getEnd()) {
                    String sepContent = sentence.substring(first.getStart(), second.getEnd());
                    first = new Separator(first.getStart(), second.getEnd(), sepContent);
                    index++;
                    continue;
                } else if (first.getEnd() >= second.getEnd()) {
                    index++;
                    continue;
                } else {
                    mergeResult.add(first);
                    break;
                }
            }
            // Add last part to the result list
            if (index == separators.size() - 1) {
                mergeResult.add(first);
            }
            index++;
        }
        return mergeResult;
    }
}
