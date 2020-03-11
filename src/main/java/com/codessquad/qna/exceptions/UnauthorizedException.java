package com.codessquad.qna.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {
    //TODO 직렬화 객체의 UID pattern 대해 학습하자.

    public static final String NO_MATCH_USER = "일치하는 사용자가 존재하지 않습니다.";
    public static final String NO_MATCH_PASSWORD = "비밀번호가 일치하지 않습니다.";

    public UnauthorizedException(String message) {
        super(message);
    }
}
