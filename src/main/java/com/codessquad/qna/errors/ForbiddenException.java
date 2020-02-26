package com.codessquad.qna.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "잘못된 접근입니다.")
public class ForbiddenException extends RuntimeException {
}
