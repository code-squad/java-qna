package com.codessquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {
    private List<Question> questions = new ArrayList<>();

    @GetMapping("/qna/form")
    public String getQnaForm() {
        return "qna-form";
    }

    @PostMapping("/questions")
    public String createQuestion(Question question) {
        System.out.println("question -> " + question);
        questions.add(question);
        return "redirect:/";
    }
}