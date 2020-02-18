package com.codessquad.qna;

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

    private List<Question> questions = new ArrayList<>();

    @PostMapping("/questions")
    public String submitQuestion(Question newQuestion) {
        newQuestion.setCreatedDatetime(LocalDateTime.now());
        newQuestion.setQuestionId(questions.size() + 1);
        questions.add(newQuestion);
        return "redirect:/";
    }

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("questions", questions);
        return "index";
    }

    @GetMapping("/questions/{questionId}")
    public String detail(@PathVariable("questionId") int questionId, Model model) {
        for (Question question : questions) {
            if (question.getQuestionId() == questionId) {
                model.addAttribute("question", question);
                break;
            }
        }
        return "qna/show";
    }
}
