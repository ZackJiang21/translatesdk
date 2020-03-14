package org.iiai.translate.model;

import org.iiai.translate.constant.SentenceType;

public class Sentence {
    private String sentContent;

    private SentenceType type;

    public Sentence(String sentContent, SentenceType type) {
        this.sentContent = sentContent;
        this.type = type;
    }

    public String getSentContent() {
        return sentContent;
    }

    public void setSentContent(String sentContent) {
        this.sentContent = sentContent;
    }

    public SentenceType getType() {
        return type;
    }

    public void setType(SentenceType type) {
        this.type = type;
    }
}
