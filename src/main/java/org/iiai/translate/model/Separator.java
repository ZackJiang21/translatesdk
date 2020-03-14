package org.iiai.translate.model;

import java.util.Objects;

public class Separator {
    private int start;

    private int end;

    private String content;

    public Separator() {
    }

    public Separator(int start, int end, String content) {
        this.start = start;
        this.end = end;
        this.content = content;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Separator separator = (Separator) o;
        return start == separator.start &&
                end == separator.end &&
                content.equals(separator.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end, content);
    }
}
