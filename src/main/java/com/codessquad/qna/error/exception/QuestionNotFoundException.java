package com.codessquad.qna.error.exception;

public class QuestionNotFoundException extends EntityNotFoundException {

    public QuestionNotFoundException(String message) {
        super(message, ErrorCode.QUESTION_NOT_FOUND);
    }
}
