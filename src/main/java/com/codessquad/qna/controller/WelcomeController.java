package com.codessquad.qna.controller;

import com.codessquad.qna.domain.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(WelcomeController.class);


    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/")
    public String Home(Model model) {
        LOGGER.debug("[page] : {}", "메인");
        model.addAttribute("questions", questionRepository.findAll());
        return "home";
    }

}
