package com.codessquad.qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @RequestMapping(value = "/qna/form", method = {RequestMethod.GET})
    public String createQuestion() {
        return "/qna/form";
    }

    @RequestMapping(value = "/qna/form", method = {RequestMethod.POST})
    public String makeQuestion(Question question, Model model) {
        questionRepository.save(question);
        return "redirect:/";
    }

    @RequestMapping(value = {"/", "/index"}, method = {RequestMethod.GET})
    public String showQuestionList(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "/index";
    }

    @RequestMapping(value = "/questions/{questionIndex}", method = RequestMethod.GET)
    public ModelAndView questionShowDetail(@PathVariable Long questionIndex) {
        ModelAndView modelAndView = new ModelAndView("/qna/show");
        modelAndView.addObject("question", questionRepository.getOne(questionIndex));
        return modelAndView;
    }
}
