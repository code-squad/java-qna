package com.codessquad.qna.exception;

public class CustomNoSuchUserException extends CustomBaseException{
    public CustomNoSuchUserException(String path, String errorMessage) {
        super(path, errorMessage);
    }
}
