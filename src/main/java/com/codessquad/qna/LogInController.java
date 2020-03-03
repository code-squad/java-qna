package com.codessquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogInController {
    @GetMapping("/user/login")
    public String logIn() {
        return "login";
    }
}
