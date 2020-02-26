package com.codessquad.qna;

import com.codessquad.qna.domain.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("questions", questionRepository.findAll());

        return "index";
    }

}
