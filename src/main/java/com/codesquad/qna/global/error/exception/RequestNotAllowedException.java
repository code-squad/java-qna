package com.codesquad.qna.global.error.exception;

public class RequestNotAllowedException extends BusinessException {
    public RequestNotAllowedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
