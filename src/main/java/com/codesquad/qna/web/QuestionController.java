package com.codesquad.qna.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {
    List<Question> questionList = new ArrayList<>();

    @GetMapping("/questions")
    public String questions() {
        return "/qna/form";
    }

    @PostMapping("/questions")
    public String writeQuestion(Question question) {
        System.out.println(question);
        questionList.add(question);

        return "redirect:/";
    }

    @GetMapping("")
    public String home(Model model) {
        model.addAttribute("questions", questionList);

        return "index";
    }


}
