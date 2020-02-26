package com.codessquad.qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.GeneratedValue;
import javax.servlet.http.HttpSession;

@Controller
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/questions/form")
    public String form(HttpSession session) {
        if (HttpSessionUtils.isLoginUser(session)) {
            System.out.println("Login First");
            return "/users/loginForm";
        }
        return "qna/form";
    }

    @PostMapping("/questions")
    public String create(String title, String contents, HttpSession session) {
        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        Question newQuestion = new Question(sessionUser.getUserId(), title, contents);
        System.out.println("posted");
        return "redirect:/";

    }
//
//    @GetMapping("/")
//    public String viewQnaList(Model model) {
//        model.addAttribute("questions", questionRepository.findAll());
//        return "/index";
//    }


}
