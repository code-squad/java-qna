package com.codessquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
        questions.add(question);
        question.setIndex(questions.size());
        question.setLocalDateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        System.out.println("question : " + question);
        return "redirect:/question/show/" + questions.size();
    }

    @GetMapping("/question/show/{index}")
    public String show(Model model, @PathVariable int index) {
        for (int i = 0; i < questions.size(); i++) {
            if (questions.size() - 1 == i) {
                model.addAttribute("title", questions.get(i).getTitle());
                model.addAttribute("writer", questions.get(i).getWriter());
                model.addAttribute("contents", questions.get(i).getContents());
                model.addAttribute("time", questions.get(i).getLocalDateTime());
                model.addAttribute("index", index);
                return "qna/show";
            }
        }
        return "qna/show";
    }
}
