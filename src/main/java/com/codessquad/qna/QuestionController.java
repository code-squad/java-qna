package com.codessquad.qna;

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

    @GetMapping("/qna/form")
    public String questionForm() {
        return "questionForm";
    }

    @PostMapping("/questions")
    public String questions(Question question) {
        question.setId(questions.size()+1);
        questions.add(question);
        System.out.println("question : "+ question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("questions", questions);
        return "index";
    }

    @GetMapping("/question/{id}")
    public String questionPage(@PathVariable int id, Model model) {
        for (Question each : questions) {
            if (each.getId() == id) model.addAttribute("question", each);
        }
        return "show";
    }

}
