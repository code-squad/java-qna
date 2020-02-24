package com.codessquad.qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("/qna/form")
    public String question(Question question) {
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "index";
    }

    @GetMapping("/questions/{id}")
    public String post(@PathVariable("id") Long id, Model model) {
        if (!questionRepository.existsById(id)) {
            return "redirect:/";
        }
        model.addAttribute("question", questionRepository.findById(id));
        return "qna/show";
    }
}
