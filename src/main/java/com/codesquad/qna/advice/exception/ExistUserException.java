package com.codesquad.qna.advice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Already exists")
public class ExistUserException extends RuntimeException {
    public ExistUserException(String message) {
        super(message);
    }

    public static ExistUserException existUser() {
        return new ExistUserException("이미 존재하는 아이디입니다");
    }
}
