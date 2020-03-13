package com.codessquad.qna.exception;

public class NoSuchElementException extends BaseException {
    public NoSuchElementException(String path, String errorMessage) {
        super(path, errorMessage);
    }
}
