package com.codessquad.qna.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {
    private List<Question> questions = new ArrayList<>();

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("questions", questions);
        return "index";
    }

    @GetMapping("/questions/{index}")
    public String question(@PathVariable int index, Model model) {
        Question question = questions.get(index - 1);
        model.addAttribute("question", question);
        return "qna/show";
    }

    @PostMapping("/questions")
    public String createQuestion(Question question) {
        System.out.println(question);
        questions.add(question);
        return "redirect:/";
    }
}
