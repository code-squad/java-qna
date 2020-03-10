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
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/user/login";
        }
        return "/qna/form";
    }

    @PostMapping("/qna/form")
    public String makeQuestion(String title, String contents, HttpSession session) {
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Question question = new Question(loginUser.getUserId(),title,contents);
        System.out.println(question.toString());
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
