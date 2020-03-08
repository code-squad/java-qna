package com.codessquad.qna.common.error.exception;

public class LoginRequiredException extends BusinessException {

    public LoginRequiredException() {
        super(ErrorCode.LOGIN_REQUIRED);
    }

    public LoginRequiredException(String message) {
        super(message, ErrorCode.LOGIN_REQUIRED);
    }
}
