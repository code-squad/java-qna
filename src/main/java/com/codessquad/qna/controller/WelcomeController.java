package com.codessquad.qna.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

    @GetMapping()
    public String welcomeIndex() {
        return "index";
    }

    @GetMapping("/user/form")
    public String getSignUpForm() {
        return "form";
    }

}
