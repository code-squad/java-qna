package com.codesquad.qna.global.error.exception;

public class DataNotFoundException extends BusinessException {

    public DataNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
