package com.codessquad.qna;

import com.codessquad.qna.exception.CanNotDeleteException;
import com.codessquad.qna.exception.InvalidInputException;
import com.codessquad.qna.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public String notFound(NotFoundException e, Model model, HttpServletRequest request) {
        LOGGER.debug("[page/EXCEPTION] : {}", "NOT_FOUND");
        setModel(e,request,model);
        return "/errors/404";
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(IllegalStateException.class)
    public String illegal(IllegalStateException e, Model model, HttpServletRequest request) {
        LOGGER.debug("[page/EXCEPTION] : {}", "UNAUTHORIZED");
        setModel(e,request,model);
        return "/errors/401";
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(IllegalArgumentException.class)
    public String notMatch(IllegalArgumentException e, Model model, HttpServletRequest request) {
        LOGGER.debug("[page/EXCEPTION] : {}", "NOT_MATCH_PASSWORD");
        setModel(e,request,model);
        return "/errors/403";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidInputException.class)
    public String invalidInput(InvalidInputException e, Model model, HttpServletRequest request) {
        LOGGER.debug("[page/EXCEPTION] : {}", "BAD_REQUEST");
        setModel(e,request,model);
        return "/errors/400";
    }

    //TODO: 상태코드 수정?
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CanNotDeleteException.class)
    public String canNotDelete(CanNotDeleteException e, Model model, HttpServletRequest request) {
        LOGGER.debug("[page/EXCEPTION] : {}", "BAD_REQUEST");
        setModel(e,request,model);
        return "/errors/400";
    }

    private void setModel(Exception e, HttpServletRequest request, Model model) {
        model.addAttribute("timestamp", LocalDateTime.now());
        model.addAttribute("path", request.getRequestURI());
        model.addAttribute("errorMessage", e.getMessage());
    }

}
