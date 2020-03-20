package org.iiai.translate.model.type;

import org.iiai.translate.constant.SpecSymbolType;

public class SpecSymbol extends SentenceType {
    private SpecSymbolType type;

    public SpecSymbol() {
    }

    public SpecSymbol(SpecSymbolType type) {
        this.type = type;
    }

    public SpecSymbolType getType() {
        return type;
    }

    public void setType(SpecSymbolType type) {
        this.type = type;
    }
}
