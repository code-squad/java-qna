package com.codessquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")

public class QuestionController {
    @GetMapping("/form")
    public String form(HttpSession session) {
        if (HttpSessionUtils.isLoginUser(session)) {
            return "/users/loginForm";
        }

        return "qna/form";
    }

    @PostMapping("")
    public String create(String title, String contents, HttpSession session) {
        if (HttpSessionUtils.isLoginUser(session)) {
            return "/users/loginForm";
        }
        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        return "redirect:/";


    }
}
