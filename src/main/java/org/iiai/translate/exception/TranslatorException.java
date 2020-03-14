package org.iiai.translate.exception;

public class TranslatorException extends RuntimeException {
    private int errorCode;

    public TranslatorException(int errorCode) {
        this.errorCode = errorCode;
    }


    public TranslatorException(int errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
