package com.codessquad.qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/qna/form")
    public String createQuestion(HttpSession session) {
        Object value = session.getAttribute("user");

        if(value != null){
            return "/qna/form";
        }
        return "/users/login";
    }

    @PostMapping("/qna/form")
    public String makeQuestion(Question question, Model model) {
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String showQuestionList(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "/home";
    }

    @GetMapping("/questions/{questionIndex}")
    public ModelAndView questionShowDetail(@PathVariable Long questionIndex) {
        ModelAndView modelAndView = new ModelAndView("/qna/show");
        modelAndView.addObject("question", questionRepository.getOne(questionIndex));
        return modelAndView;
    }
}
