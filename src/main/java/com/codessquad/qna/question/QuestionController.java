package com.codessquad.qna.question;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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

    @GetMapping("/{id}")
    public ModelAndView showQuestionContents(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("qna/show");
        mav.addObject("question", questionRepository.findById(id).get());
        return mav;
    }
}
