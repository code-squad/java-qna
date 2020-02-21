package com.codessquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class QnaController {
    public List<Question> questions = new ArrayList<>();

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("question", questions);
        return "index";
    }

    @PostMapping("/question/create")
    public String create(Question question) {
        System.out.println("question : " + question);
        questions.add(question);
        question.setIndex(questions.size());
        question.setLocalDate(LocalDate.now());
        return "redirect:/question/show";
    }

    @GetMapping("/question/show")
    public String show(Model model) {
        for (int i = 0; i < questions.size(); i++) {
            if (questions.size() - 1 == i) {
                model.addAttribute("title", questions.get(i).getTitle());
                model.addAttribute("writer", questions.get(i).getWriter());
                model.addAttribute("contents", questions.get(i).getContents());
                model.addAttribute("time", questions.get(i).getLocalDate());
                return "qna/show";
            }
        }
        return "qna/show";
    }
}
