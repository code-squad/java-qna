package com.codesquad.qna.advice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }

    public static UnauthorizedException notLogin() {
        return new UnauthorizedException("로그인 해주세요");
    }

    public static InputMistakeException noMatchUser() {
        return new InputMistakeException("일치하는 사용자가 존재하지 않습니다");
    }

    public static InputMistakeException noMatchPassword() {
        return new InputMistakeException("비밀번호가 일치하지 않습니다");
    }
}
