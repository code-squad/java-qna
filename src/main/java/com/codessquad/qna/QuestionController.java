package com.codessquad.qna;

import com.codessquad.domain.Question;
import com.codessquad.domain.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/questions/form")
    public String form() {
        return "/qna/form";
    }

    @PostMapping("/questions")
    public String create(Question newQuestion) {
        newQuestion.setDateTime(LocalDateTime.now());
        questionRepository.save(newQuestion);
        return "redirect:/";
    }

    @GetMapping("")
    public String main(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "/main";
    }
    
}
