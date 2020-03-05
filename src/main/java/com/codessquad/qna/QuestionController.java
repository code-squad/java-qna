package com.codessquad.qna;

import com.codessquad.domain.Question;
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

    @GetMapping("/questions/form")
    public String form() {
        return "/qna/form";
    }

    @PostMapping("/questions")
    public String create(Question newQuestion) {
        newQuestion.setDateTime(LocalDateTime.now());
        newQuestion.setQuestionIndex(questions.size() + 1);
        questions.add(newQuestion);
        System.out.println(newQuestion);
        return "redirect:/";
    }

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("questions", questions);
        return "/main";
    }

    @GetMapping("questions/{questionIndex}")
    public String show(@PathVariable long questionIndex, Model model) {
        model.addAttribute("question",checkIndex(questionIndex));
        return "qna/show";
    }

    private Question checkIndex(Long questionIndex) {
        for (Question question : questions) {
            if (questionIndex.equals(question.getQuestionIndex()))
                return question;
        }
        return null;
    }
}
