package com.codessquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {
    private List<Question> questions = new ArrayList<>();

    @PostMapping("/qna/form")
    public String question(Question question) {
        this.questions.add(question);
        System.out.println(question);
        return "redirect:/";
    }
}
