package com.codessquad.qna.exception;

public class CanNotDeleteException extends RuntimeException {
    public CanNotDeleteException() {}

    public CanNotDeleteException(String message) {
        super(message);
    }
}
