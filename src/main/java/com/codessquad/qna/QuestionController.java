package com.codessquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {
    private List<Question> questions = new ArrayList<>();

    @GetMapping("/qna/form")
    public String form() {
        return "qna/form";
    }

    @PostMapping("/questions")
    public String ask(Question question) {
        question.setCurrentTime(LocalDateTime.now());
        question.setPostIndex(questions.size()+1);
        questions.add(question);
        return "redirect:/";
    }

    @GetMapping("/qna")
    public String list(Model model) {
        model.addAttribute("questions", questions);
        return "qna/show";
    }

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("questions", questions);
        return "/index";
    }
}
