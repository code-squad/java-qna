package com.codesquad.qna.web;

import com.codesquad.qna.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String showMain(Model model) {
        model.addAttribute("questions", questionService.findAll());
        return "main";
    }
}
