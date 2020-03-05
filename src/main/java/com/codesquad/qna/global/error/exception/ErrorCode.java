package com.codesquad.qna.global.error.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    FORBIDDEN(HttpStatus.FORBIDDEN.value(), "You can only modify your own"),
    DATA_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Data is not found"),
    DELETE_FAILED(HttpStatus.FORBIDDEN.value(), "Can't delete it");

    private final int status;
    private final String message;

    private ErrorCode(final int status, final String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
