package com.codessquad.qna.exception;

public class CustomUnauthorizedException extends CustomBaseException{
    public CustomUnauthorizedException(String path, String errorMessage) {
        super(path, errorMessage);
    }
}
