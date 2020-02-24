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

    @GetMapping("/question/form")
    public String createQuestionForm() {
        return "qna/form";
    }

    @PostMapping("/questions")
    public String createQuestion(Question question) {
        question.setCurrentTime(LocalDateTime.now());
        question.setPostIndex(questions.size() + 1);
        questions.add(question);
        return "redirect:/";
    }

    @GetMapping("/questions/{postIndex}")
    public String showQuestion(@PathVariable int postIndex, Model model) {
        Question question = questions.get(postIndex - 1);
        if(question!=null) {
            model.addAttribute("question", question);
            return "qna/show";
        }
        return "redirect:/error/notFound.html";
    }

    @GetMapping("/")
    public String listQuestions(Model model) {
        model.addAttribute("questions", questions);
        return "home";
    }
}