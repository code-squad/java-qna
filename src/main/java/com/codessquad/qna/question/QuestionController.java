package com.codessquad.qna.question;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;


@Controller
public class QuestionController {
    private List<Question> questions = new ArrayList<>();

    @GetMapping("/")
    public String questionList(Model model) {
        model.addAttribute("questions", questions);
        return "index";
    }

    @PostMapping("/qna/question")
    public String qna(Question question) {
        System.out.println(question);
        questions.add(question);
        return "redirect:/";
    }

}



