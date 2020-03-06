package com.codessquad.qna.exception;

public class CustomWrongFormatException extends CustomBaseException {
    public CustomWrongFormatException(String path, String errorMessage) {
        super(path, errorMessage);
    }
}
