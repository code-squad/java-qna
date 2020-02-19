package com.codessquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QuestionController {

    @PostMapping("/questions")
    public String createQuestion() {

        return "redirect:/";
    }
}
