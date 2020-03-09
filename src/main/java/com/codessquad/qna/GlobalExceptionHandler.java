package com.codessquad.qna;

import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LogManager.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NullPointerException.class)
    public String handle(NullPointerException e, Model model, HttpServletRequest request) {
        LOGGER.debug("[page]NULL");
        model.addAttribute("timestamp", LocalDateTime.now());
        model.addAttribute("error", "NOT_FOUND");
        model.addAttribute("path", request.getRequestURI());
        model.addAttribute("errorMessage", e.getMessage());
        return "/errors/404";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public String notFound(NotFoundException e, Model model, HttpServletRequest request) {
        LOGGER.debug("[page]NOT_FOUND");
        model.addAttribute("timestamp", LocalDateTime.now());
        model.addAttribute("error", "NOT_FOUND");
        model.addAttribute("path", request.getRequestURI());
        model.addAttribute("errorMessage", e.getMessage());
        return "/errors/404";
}

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalStateException.class)
    public String illegal(IllegalStateException e, Model model, HttpServletRequest request) {
        LOGGER.debug("[page]BAD_REQUEST");
        model.addAttribute("timestamp", LocalDateTime.now());
        model.addAttribute("error", "BAD_REQUEST");
        model.addAttribute("path", request.getRequestURI());
        model.addAttribute("errorMessage", e.getMessage());
        return "/errors/400";
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(NoSuchElementException.class)
    public String notMatch(NoSuchElementException e, Model model, HttpServletRequest request) {
        LOGGER.debug("[page]NOT_MATCH");
        model.addAttribute("timestamp", LocalDateTime.now());
        model.addAttribute("error", "NOT_MATCH");
        model.addAttribute("path", request.getRequestURI());
        model.addAttribute("errorMessage", e.getMessage());
        return "/errors/403";
    }

}
