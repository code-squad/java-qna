package com.codessquad.qna.web;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.QuestionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QuestionController {

    private Logger logger = LogManager.getLogger(QuestionController.class);

    @Autowired
    QuestionRepository questionRepository;

    @GetMapping("/")
    public String listPage(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "questions/home";
    }

    @GetMapping("/questions/createForm")
    public String createFormPage() {
        return "questions/createForm";
    }

    @GetMapping("/questions/{id}")
    public String detailPage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("question", questionRepository.getOne(id));
        return "questions/detail";
    }

    @PostMapping("/questions")
    public String createQuestion(Question question) {
        questionRepository.save(question);
        return "redirect:/";
    }
}
