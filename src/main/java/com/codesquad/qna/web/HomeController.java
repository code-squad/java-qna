package com.codesquad.qna.web;

import com.codesquad.qna.domain.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private QuestionRepository questionRepository;

    public static final String HOME_DIRECTORY = "redirect:/";
    public static final String NOT_AUTHORIZE_DIRECTORY = "/forbidden";

    @GetMapping("/")
    public String showQuestionList(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "/home";
    }
}
