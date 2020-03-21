package com.codesquad.qna.web;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFoundException(UserNotFoundException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        // statusCode - 404

        return "/user/login_failed";
    }

    @ExceptionHandler(QuestionNotFoundException.class)
    public String handleQuestionNotFoundException(QuestionNotFoundException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        // statusCode - 404

        return "/qna/question_not_found";
    }

    @ExceptionHandler(UserNotPermittedException.class)
    public String handleUserNotPermittedException(UserNotPermittedException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        // statusCode - 401 or 403

        return "/user/user_not_permitted";
    }

    @ExceptionHandler(LoginFailedException.class)
    public String handleLoginFailedException(LoginFailedException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        // statusCode - 400

        return "/user/login_failed";
    }
}
