package com.codesquad.qna.web;

import com.codesquad.qna.domain.Question;
import com.codesquad.qna.domain.QuestionRepostory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private QuestionRepostory questionRepostory;

    @GetMapping("/form")
    public String moveQnaForm() {
        return "/questions/form";
    }

    @PostMapping("")
    public String saveQuestions(Question question) {
        questionRepostory.save(question);
        return "redirect:/questions";
    }

    @GetMapping("/{id}")
    public ModelAndView viewQuestion(@PathVariable long id) { //여기서는 long으로
        ModelAndView mav = new ModelAndView("/questions/show");
        mav.addObject("currentQuestion", questionRepostory.findById(id).get());
        return mav;
    }

    @GetMapping("")
    public String viewQnaList(Model model) {
        model.addAttribute("questions", questionRepostory.findAll());
        return "/questions/list";
    }

}
