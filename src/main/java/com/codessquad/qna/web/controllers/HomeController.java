package com.codessquad.qna.web.controllers;

import com.codessquad.qna.web.services.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final QuestionService questionService;

    public HomeController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public String homePage(Model model) {
        model.addAttribute("questions", questionService.getAllQuestions());
        return "home";
    }
}
