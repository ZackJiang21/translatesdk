package org.iiai.translate.filter;

import org.iiai.translate.model.Separator;

import java.util.List;

public interface SeparatorFilter {
    List<Separator> getSeparators(String content);
}
