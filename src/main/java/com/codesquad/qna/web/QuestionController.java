package com.codesquad.qna.web;

import com.codesquad.qna.domain.Question;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
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
    public String saveQuestions(Question question) {
        question.setIndex(questions.size() + 1);
        question.setCreatedTime(LocalDateTime.now());
        questions.add(question);
        return "redirect:/qna";
    }

    @GetMapping("/questions/{index}")
    public String viewQuestion(Model model, @PathVariable int index) {
        for (Question question : questions) {
            if (question.getIndex() == index) {
                model.addAttribute("currentQuestion", question);
                return "/qna/show";
            }
        }
        return "redirect:/qna";
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
