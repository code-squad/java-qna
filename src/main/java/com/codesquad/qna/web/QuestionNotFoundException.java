package com.codesquad.qna.web;

public class QuestionNotFoundException extends RuntimeException {
    // statusCode - 404

    public QuestionNotFoundException() {
        super("해당 글을 찾을 수 없습니다.");
    }

    public QuestionNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
