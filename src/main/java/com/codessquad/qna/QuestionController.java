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

    @PostMapping("/questions")
    public String createQuestion(Question question) {
        String id = Integer.toString(questions.size() + 1);
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        question.setId(id);
        question.setTime(time);
        questions.add(question);

        return "redirect:/";
    }

    @GetMapping("/")
    public String showQuestionList(Model model) {
        model.addAttribute("questions", questions);

        return "qna/list";
    }

    @GetMapping("/questions/{index}")
    public String showQuestionDetail(@PathVariable String index, Model model) {
        questions.stream()
                .filter(question -> question.getId().equals(index))
                .forEach(question -> model.addAttribute("question", question));

        return "qna/show";
    }
}
