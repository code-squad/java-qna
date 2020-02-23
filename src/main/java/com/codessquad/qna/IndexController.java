package com.codessquad.qna;

import org.springframework.web.bind.annotation.GetMapping;

public class IndexController {


    @GetMapping("/user/login")
    public String login() {
        return "login";
    }

    @GetMapping("/user/form")
    public String createForm() {
        return "form";
    }
}
