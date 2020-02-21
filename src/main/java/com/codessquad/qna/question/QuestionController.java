package com.codessquad.qna.question;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {
    private List<Question> questions = new ArrayList<>();

    @PostMapping("/questions")
    public String qna(Question question) {
        question.setQuestionNumber(questions.size() + 1);
        questions.add(question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String questionList(Model model) {
        model.addAttribute("questions", questions);
        return "main";
    }

    @GetMapping("/questions/{questionNumber}")
    public String questionContents(@PathVariable int questionNumber, Model model) {
        Question question = questions.get(questionNumber - 1);
        model.addAttribute("question", question);
        return "qna/show";
    }
}



