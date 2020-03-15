package com.codessquad.qna.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    //TODO 직렬화 객체의 UID pattern 대해 학습하자.

    public NotFoundException(Long id) {
        this(id.toString());
    }

    public NotFoundException(String message) {
        super(message + " 존재하지 않습니다.");
    }
}
