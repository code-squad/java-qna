package com.codessquad.qna.controller;

import com.codessquad.qna.exception.ProductNotfoundException;
import com.codessquad.qna.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ProductExceptionController {

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = ProductNotfoundException.class)
    public String exception(Model model) {
        model.addAttribute("errorMessage", "해당 페이지가 없습니다.");
        return "error";
    }

    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = UnauthorizedException.class)
    public String unauthorizedException(Model model) {
        model.addAttribute("errorMessage", "접근 권한이 없습니다.");
        return "error";
    }
}
