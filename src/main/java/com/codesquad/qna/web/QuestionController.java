package com.codesquad.qna.web;

import com.codesquad.qna.domain.Question;
import com.codesquad.qna.domain.QuestionRepostory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionRepostory questionRepostory;

    @GetMapping("/qna/form")
    public String moveQnaForm() {
        return "/qna/form";
    }

    @PostMapping("/questions/create")
    public String saveQuestions(Question question) {
        question.setCreatedTime();
        questionRepostory.save(question);
        return "redirect:/qna";
    }

    @GetMapping("/questions/{id}")
    public ModelAndView viewQuestion(@PathVariable long id) { //여기서는 long으로
        ModelAndView mav = new ModelAndView("/qna/show");
        mav.addObject("currentQuestion", questionRepostory.findById(id).get());
        return mav;
    }

    @GetMapping("/qna")
    public String viewQna(Model model) {
        model.addAttribute("questions", questionRepostory.findAll());
        return "/qna/list";
    }

    @GetMapping("/")
    public String viewQuestions() {
        return "/index";
    }

}
