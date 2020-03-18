package com.codessquad.qna.web.controllers;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.web.services.AuthService;
import com.codessquad.qna.web.services.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String detailPage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("question", questionService.getQuestionById(id));
        return "questions/detail";
    }

    @GetMapping("/createForm")
    public String createFormPage(HttpServletRequest request) {
        authService.getRequester(request);
        return "questions/createForm";
    }

    @GetMapping("/{id}/updateForm")
    public String updateFormPage(HttpServletRequest request, @PathVariable("id") Long targetQuestionId, Model model) {
        Question targetQuestion = questionService.getQuestionById(targetQuestionId);
        authService.hasAuthorization(request, targetQuestion);
        model.addAttribute("question", targetQuestion);
        return "questions/updateForm";
    }

    @PostMapping
    public String createQuestion(HttpServletRequest request, Question question) {
        question.setWriter(authService.getRequester(request));
        questionService.register(question);
        return "redirect:/";
    }

    @PutMapping("/{id}")
    public String updateQuestion(HttpServletRequest request, @PathVariable("id") Long targetQuestionId, Question newQuestion) {
        Question targetQuestion = questionService.getQuestionById(targetQuestionId);
        authService.hasAuthorization(request, targetQuestion);
        questionService.edit(targetQuestion, newQuestion);
        return String.format("redirect:/questions/%d", targetQuestionId);
    }
}
