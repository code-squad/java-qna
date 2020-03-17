package com.codessquad.qna.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomAdvice {
    @ExceptionHandler(CustomNoSuchElementException.class)
    public String handleError(CustomNoSuchElementException noElement) {
        return noElement.getPath();
    }

    @ExceptionHandler(UnauthorizedException.class)
    public String handleError(UnauthorizedException unauthorized) {
        return unauthorized.getPath();
    }

    @ExceptionHandler(NoSuchUserException.class)
    public String handleError(NoSuchUserException noUser) {
        return noUser.getPath();
    }

    @ExceptionHandler(WrongFormatException.class)
    public String handleError(WrongFormatException wrongFormat) {
        return wrongFormat.getPath();
    }
}
