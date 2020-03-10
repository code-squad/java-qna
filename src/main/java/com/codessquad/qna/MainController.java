package com.codessquad.qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("")
    public String main() {
        return "main";
    }

    @GetMapping("/main")
    public String backToMain() {
        return "main";
    }

    @GetMapping("/posts")
    public String showPosts(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "QuestionList";
    }
}
