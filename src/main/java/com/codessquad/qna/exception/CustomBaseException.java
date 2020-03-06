package com.codessquad.qna.exception;

public class CustomBaseException extends RuntimeException {
    private String path;
    private String errorMessage;

    public CustomBaseException(String path, String errorMessage) {
        this.path = path;
        this.errorMessage = errorMessage;
    }

    public String getPath() {
        return path;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
