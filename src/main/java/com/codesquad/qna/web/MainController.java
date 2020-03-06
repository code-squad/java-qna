package com.codesquad.qna.web;

import com.codesquad.qna.domain.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/")
    public String showMain(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "main";
    }
}
