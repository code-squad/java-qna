package com.codessquad.qna.web.controllers;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.exceptions.NotFoundException;
import com.codessquad.qna.exceptions.UnauthorizedException;
import com.codessquad.qna.web.services.AuthService;
import com.codessquad.qna.web.services.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    private final AuthService authService;
    private final QuestionService questionService;

    public QuestionController(AuthService authService, QuestionService questionService) {
        this.authService = authService;
        this.questionService = questionService;
    }

    @GetMapping("/{id}")
    public String detailPage(@PathVariable("id") Long id, Model model) throws NotFoundException {
        model.addAttribute("question", questionService.getQuestionById(id));
        return "questions/detail";
    }

    @GetMapping("/createForm")
    public String createFormPage(HttpServletRequest request) throws UnauthorizedException {
        authService.getRequester(request);
        return "questions/createForm";
    }

    @PostMapping("/questions")
    public String createQuestion(HttpServletRequest request, Question question) throws UnauthorizedException {
        question.setWriter(authService.getRequester(request));
        questionService.register(question);
        return "redirect:/";
    }
}
