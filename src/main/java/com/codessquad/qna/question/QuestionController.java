package com.codessquad.qna.question;

import com.codessquad.qna.user.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    private static Logger log = LoggerFactory.getLogger(QuestionController.class);
    private List<Question> questions = new ArrayList<>();

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("")
    public String qnaCreate(Question question) {
        log.info("Question : '{}' ", question.toString());
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/form")
    public String qnaForm() {
        return "qna/form";
    }

    @GetMapping("/{questionNumber}")
    public String questionContents(@PathVariable int questionNumber, Model model) {
        Question question = questions.get(questionNumber - 1);
        model.addAttribute("question", question);
        return "qna/show";
    }
}



