package com.codessquad.qna.web.controllers;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
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
        User requester = authService.getRequester(request);
        Question targetQuestion = questionService.getQuestionById(targetQuestionId);
        requester.hasAuthorization(targetQuestion);
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
        User requester = authService.getRequester(request);
        Question targetQuestion = questionService.getQuestionById(targetQuestionId);
        requester.hasAuthorization(targetQuestion);
        questionService.edit(targetQuestion, newQuestion);
        return String.format("redirect:/questions/%d", targetQuestionId);
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(HttpServletRequest request, @PathVariable("id") Long targetQuestionId) {
        User requester = authService.getRequester(request);
        Question targetQuestion = questionService.getQuestionById(targetQuestionId);
        requester.hasAuthorization(targetQuestion);
        questionService.delete(targetQuestion);
        return "redirect:/";
    }
}
