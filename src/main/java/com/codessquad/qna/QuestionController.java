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

    @PostMapping("/qna/form")
    public String question(Question question) {
        question.setId(this.questions.size() + 1);
        this.questions.add(question);
        System.out.println(question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("questions", questions);
        return "index";
    }

    @GetMapping("/questions/{id}")
    public String post(@PathVariable("id") int id, Model model) {
        if (id > questions.size()) {
            return "redirect:/";
        }

        model.addAttribute("question", questions.get(id - 1));
        return "qna/show";
    }
}
