package com.codessquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    private List<Question> questions = new ArrayList<>();

    @GetMapping("/form")
    public String createForm() {
        return "qna/form";
    }

    @PostMapping("/create")
    public String create(Question question, Model model) {
        question.setCurrentTime(LocalDateTime.now());
        question.setId(questions.size() + 1);
        questions.add(question);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String read(@PathVariable Integer id, Model model) {
        Question question = questions.get(id - 1);
        model.addAttribute("question", question);
        return "qna/show";
    }

    @GetMapping
    public String main(Model model) {
        model.addAttribute("questions", questions);
        return "main";
    }
}
