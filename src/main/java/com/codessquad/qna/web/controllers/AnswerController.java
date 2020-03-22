package com.codessquad.qna.web.controllers;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.web.services.AnswerService;
import com.codessquad.qna.web.services.AuthService;
import com.codessquad.qna.web.services.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

    private final AuthService authService;
    private final AnswerService answerService;
    private final QuestionService questionService;

    public AnswerController(AuthService authService, AnswerService answerService, QuestionService questionService) {
        this.authService = authService;
        this.answerService = answerService;
        this.questionService = questionService;
    }

    @PostMapping
    public String createAnswer(HttpServletRequest request, @PathVariable("questionId") Long questionId, Answer answer) {
        User writer = authService.getRequester(request);
        answer.setWriter(writer);
        answer.setQuestion(questionService.getQuestionById(questionId));
        answerService.register(answer);
        return String.format("redirect:/questions/%d", questionId);
    }
}
