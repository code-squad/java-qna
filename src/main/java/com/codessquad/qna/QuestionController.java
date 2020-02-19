package com.codessquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {
    private List<Question> questions = new ArrayList<>();
    @RequestMapping(value = "/qna/form", method = {RequestMethod.GET})
    public String createQuestion() {
        return "/qna/form";
    }

    @RequestMapping(value = "/qna/form", method = {RequestMethod.POST})
    public String saveQuestion(Question question, Model model) {
        questions.add(question);
        return "redirect:/";
    }

    @RequestMapping(value = {"/","/index"}, method = {RequestMethod.GET})
    public String showQuestionList(Model model) {
        model.addAttribute("questions",questions);
        return "/index";
    }

    @GetMapping("/qna/show")
    public String questionShowDetail() {
        return "/qua/show";
    }
}
