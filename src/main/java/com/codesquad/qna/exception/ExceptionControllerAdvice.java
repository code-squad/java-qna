package com.codesquad.qna.exception;

import com.codesquad.qna.exception.exception.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(UnauthorizedException.class)
    public String userNotFoundException() {
        return "redirect:/";
    }


}
