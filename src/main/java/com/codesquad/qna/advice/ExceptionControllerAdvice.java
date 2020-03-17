package com.codesquad.qna.advice;

import com.codesquad.qna.advice.exception.ForbiddenException;
import com.codesquad.qna.advice.exception.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(UnauthorizedException.class)
    public String userNotFound() {
        return "redirect:/";
    }

    @ExceptionHandler(ForbiddenException.class)
    public String noPermission() {
        return "redirect:/";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String illegalArgument() {
        return "redirect:/";
    }
}
