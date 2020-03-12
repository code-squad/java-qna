package com.codessquad.qna.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomAdvice {
    @ExceptionHandler(CustomNoSuchElementException.class)
    public String handleError(CustomNoSuchElementException noElement) {
        return noElement.getPath();
    }

    @ExceptionHandler(CustomUnauthorizedException.class)
    public String handleError(CustomUnauthorizedException unauthorized) {
        return unauthorized.getPath();
    }

    @ExceptionHandler(CustomNoSuchUserException.class)
    public String handleError(CustomNoSuchUserException noUser) {
        return noUser.getPath();
    }
    @ExceptionHandler(CustomWrongFormatException.class)
    public String handleError(CustomWrongFormatException wrongFormat) {
        return wrongFormat.getPath();
    }
}
