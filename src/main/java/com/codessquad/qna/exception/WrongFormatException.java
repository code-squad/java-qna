package com.codessquad.qna.exception;

public class WrongFormatException extends BaseException {
    public WrongFormatException(String path, String errorMessage) {
        super(path, errorMessage);
    }
}
