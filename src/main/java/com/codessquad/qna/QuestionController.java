package com.codessquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {
    List<Question> questions = new ArrayList<>();

    @GetMapping("/qna/form")
    public String moveQnaForm() {
        return "/qna/form";
    }

    @PostMapping("/questions")
    public String saveQuestions(Model model, Question question) {
        System.out.println("writer : " + question.getWriter() + " title : " + question.getTitle());
        question.setIndex(questions.size() + 1);
        question.setCreatedTime(LocalDateTime.now());
        questions.add(question);
        return "redirect:/qna";
    }

    @GetMapping("/questions/{index}")
    public String veiwQuestion(Model model, @PathVariable int index) {
        Question currentQuestion = null;

        for (Question question : questions) {
            if (question.getIndex() == index) {
                currentQuestion = question;
                break;
            }
        }

        model.addAttribute("currentQuestion", currentQuestion);
        return "/qna/show";
    }

    @GetMapping("/qna")
    public String viewQna(Model model) {
        model.addAttribute("questions", questions);
        return "/qna/list";
    }

    @GetMapping("/")
    public String viewQuestions() {
        return "/index";
    }

}
