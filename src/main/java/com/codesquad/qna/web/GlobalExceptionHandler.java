package com.codesquad.qna.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFoundException(UserNotFoundException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        logger.error("존재하지 않는 유저 데이터에 접근을 시도 하였습니다.");
        // statusCode - 404

        return "/user/login_failed";
    }

    @ExceptionHandler(QuestionNotFoundException.class)
    public String handleQuestionNotFoundException(QuestionNotFoundException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        logger.error("존재하지 않는 질문글 데이터에 접근을 시도 하였습니다.");
        // statusCode - 404

        return "/qna/question_not_found";
    }

    @ExceptionHandler(UserNotPermittedException.class)
    public String handleUserNotPermittedException(UserNotPermittedException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        logger.error("권한이 없는 데이터에 접근을 시도 하였습니다.");
        // statusCode - 401 or 403

        return "/user/user_not_permitted";
    }

    @ExceptionHandler(LoginFailedException.class)
    public String handleLoginFailedException(LoginFailedException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        logger.warn("비밀번호 불일치로 인해 로그인에 실패 하였습니다.");
        // statusCode - 400

        return "/user/login_failed";
    }
}
