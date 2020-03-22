package com.codesquad.qna.advice;

import com.codesquad.qna.advice.exception.ForbiddenException;
import com.codesquad.qna.advice.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;

import static com.codesquad.qna.UrlStrings.REDIRECT_LOGIN_FORM;
import static com.codesquad.qna.UrlStrings.REDIRECT_MAIN;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(UnauthorizedException.class)
    public String notLogin() {
        return REDIRECT_LOGIN_FORM.getUrl();
    }

    @ExceptionHandler(ForbiddenException.class)
    public String noPermission() {
        return REDIRECT_MAIN.getUrl();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String illegalArgument() {
        return REDIRECT_MAIN.getUrl();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public String noMatch() {
        return REDIRECT_MAIN.getUrl();
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String requestMethodNotSupported() {
        return REDIRECT_MAIN.getUrl();
    }
}
