package org.iiai.translate.model.type;

public class SingleSentType extends SentenceType {
    private boolean isNonStop;

    private boolean isLower;

    public SingleSentType() {
    }

    public SingleSentType(boolean isNonStop, boolean isLower) {
        this.isNonStop = isNonStop;
        this.isLower = isLower;
    }

    public boolean isNonStop() {
        return isNonStop;
    }

    public void setNonStop(boolean nonStop) {
        isNonStop = nonStop;
    }

    public boolean isLower() {
        return isLower;
    }

    public void setLower(boolean lower) {
        isLower = lower;
    }
}
