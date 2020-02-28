package com.codessquad.qna.controller;

import com.codessquad.qna.domain.QuestionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
    private static final Logger LOGGER = LogManager.getLogger(QuestionController.class);

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/")
    public String Home(Model model) {
        LOGGER.debug("[page]메인");
        model.addAttribute("questions", questionRepository.findAll());
        return "home";
    }

}
