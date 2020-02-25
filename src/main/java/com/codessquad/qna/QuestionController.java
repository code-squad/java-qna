package com.codessquad.qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/qna/form")
    public String viewQnaForm(HttpSession session) {
        if (!HttpSessionUtils.isLogin(session)) {
            return "/users/loginForm";
        }
        return "/qna/form";
    }

    @PostMapping("/questions")
    public String createQuestion(Question question, HttpSession session) {
        if (!HttpSessionUtils.isLogin(session)) {
            return "/users/loginForm";
        }
        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        question.setWriter(sessionUser.getUserId());
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String viewQnaList(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "/index";
    }

    @GetMapping("/questions/{id}")
    public String viewQuestionContents(@PathVariable Long id, Model model) {
        if(!(isIdPresent(id))) {
            return "redirect:/";
        }
        model.addAttribute("question", questionRepository.findById(id).get());
        return "/qna/show";
    }

    private Boolean isIdPresent(Long id) {
        return questionRepository.findById(id).isPresent();
    }
}
