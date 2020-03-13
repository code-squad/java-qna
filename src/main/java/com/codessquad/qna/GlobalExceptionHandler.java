package com.codessquad.qna;

import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NullPointerException.class)
    public String handle(NullPointerException e, Model model, HttpServletRequest request) {
        LOGGER.debug("[page/EXCEPTION] : {}", "NULL");
        setModel(e,request,model);
        return "/errors/404";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public String notFound(NotFoundException e, Model model, HttpServletRequest request) {
        LOGGER.debug("[page/EXCEPTION] : {}", "NOT_FOUND");
        setModel(e,request,model);
        return "/errors/404";
}

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalStateException.class)
    public String illegal(IllegalStateException e, Model model, HttpServletRequest request) {
        LOGGER.debug("[page/EXCEPTION] : {}", "BAD_REQUEST");
        setModel(e,request,model);
        return "/errors/400";
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(NoSuchElementException.class)
    public String notMatch(NoSuchElementException e, Model model, HttpServletRequest request) {
        LOGGER.debug("[page/EXCEPTION] : {}", "NOT_MATCH");
        setModel(e,request,model);
        return "/errors/403";
    }

    private void setModel(Exception e, HttpServletRequest request, Model model) {
        model.addAttribute("timestamp", LocalDateTime.now());
        model.addAttribute("path", request.getRequestURI());
        model.addAttribute("errorMessage", e.getMessage());
    }

}
