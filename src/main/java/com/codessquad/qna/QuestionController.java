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

    @GetMapping("/qna/form")
    public String viewQnaForm() {
        return "/qna/form";
    }

    @PostMapping("/questions")
    public String createQuestion(Question question) {
        //System.out.println("question ->\n" + question);
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String viewQnaList(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "/index";
    }

    @GetMapping("/questions/{id}")
    public String viewQuestionContents(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionRepository.findById(id).get());
        return "/qna/show";
    }
}
