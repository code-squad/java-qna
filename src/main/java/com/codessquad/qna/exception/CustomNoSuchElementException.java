package com.codessquad.qna.exception;

public class CustomNoSuchElementException extends CustomBaseException{
    public CustomNoSuchElementException(String path, String errorMessage) {
        super(path, errorMessage);
    }
}
