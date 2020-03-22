package com.codessquad.qna.exception;

public class CustomNoSuchElementException extends BaseException {
    public CustomNoSuchElementException(String path, String errorMessage) {
        super(path, errorMessage);
    }
}
