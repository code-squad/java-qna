package com.codessquad.qna.question;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;


@Controller
public class QuestionController {
    private List<Question> questions = new ArrayList<>();

    @PostMapping("/qna/question")
    public String qna(Question question) {
        questions.add(question);
        return "reDirect:/";
    }




}
