package com.codesquad.qna.web;

public enum ErrorMessage {
    ILLEGAL_STATE("You can only modify your own"),
    ILLEGAL_ARGUMENT("Not Appropriate parameters");

    private String message;

    private ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
