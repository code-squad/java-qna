package com.codessquad.qna.exception;

public class NoSuchUserException extends BaseException {
    public NoSuchUserException(String path, String errorMessage) {
        super(path, errorMessage);
    }
}
