package com.codessquad.qna.exception;

public class BaseException extends RuntimeException {
    private String path;
    private String errorMessage;

    public BaseException(String path, String errorMessage) {
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
