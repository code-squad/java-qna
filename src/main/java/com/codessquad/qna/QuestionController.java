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
    public String viewQnaForm() {
        return "/qna/form";
    }

    @PostMapping("/questions")
    public String createQuestion(Question question) {
        question.setIndex(questions.size() + 1);
        System.out.println("question ->\n" + question);
        questions.add(question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String viewQnaList(Model model) {
        model.addAttribute("questions", questions);
        return "/index";
    }

    @GetMapping("/questions/{index}")
    public String viewQuestionContents(@PathVariable("index") int index, Model model) {
        model.addAttribute("question", questions.get(index - 1));
        return "/qna/show";
    }
}
