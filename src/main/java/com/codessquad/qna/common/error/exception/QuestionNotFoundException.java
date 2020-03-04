package com.codessquad.qna.common.error.exception;

public class QuestionNotFoundException extends EntityNotFoundException {

    public QuestionNotFoundException(String message) {
        super(message, ErrorCode.QUESTION_NOT_FOUND);
    }
}
