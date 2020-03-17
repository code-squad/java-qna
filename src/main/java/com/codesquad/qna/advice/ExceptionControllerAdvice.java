package com.codesquad.qna.advice;

import com.codesquad.qna.advice.exception.ForbiddenException;
import com.codesquad.qna.advice.exception.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.codesquad.qna.UrlStrings.REDIRECT_MAIN;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(UnauthorizedException.class)
    public String userNotFound() {
        return REDIRECT_MAIN.getUrl();
    }

    @ExceptionHandler(ForbiddenException.class)
    public String noPermission() {
        return REDIRECT_MAIN.getUrl();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String illegalArgument() {
        return REDIRECT_MAIN.getUrl();
    }
}
