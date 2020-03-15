package com.codesquad.qna.web;

import com.codesquad.qna.domain.Question;
import com.codesquad.qna.domain.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/questions")
    public String questions() {
        return "/qna/form";
    }

    @PostMapping("/questions")
    public String writeQuestion(Question question, Model model) {
        questionRepository.save(question);

        return "redirect:/";
    }

    @GetMapping("")
    public String home(Model model) {
        model.addAttribute("questions", questionRepository.findAll());

        return "index";
    }
}
//
//    @GetMapping("/questions/{index}")
//    public String showQuestion(@PathVariable long index, Model model) {
//        Question selectedQuestion = questionList.get((int)index - 1);
//        model.addAttribute("question", selectedQuestion);
//
//        return "qna/show";
//    }
//}
