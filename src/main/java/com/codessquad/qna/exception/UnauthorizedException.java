package com.codessquad.qna.exception;

public class UnauthorizedException extends BaseException {
    public UnauthorizedException(String path, String errorMessage) {
        super(path, errorMessage);
    }
}
