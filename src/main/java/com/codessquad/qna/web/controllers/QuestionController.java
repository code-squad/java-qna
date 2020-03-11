package com.codessquad.qna.web.controllers;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.web.services.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }


    @GetMapping("/")
    public String listPage(Model model) {
        model.addAttribute("questions", questionService.getAllQuestions());
        return "questions/home";
    }

    @GetMapping("/questions/createForm")
    public String createFormPage() {
        return "questions/createForm";
    }

    @GetMapping("/questions/{id}")
    public String detailPage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("question", questionService.getQuestionById(id));
        return "questions/detail";
    }

    @PostMapping("/questions")
    public String createQuestion(Question question) {
        questionService.register(question);
        return "redirect:/";
    }
}
