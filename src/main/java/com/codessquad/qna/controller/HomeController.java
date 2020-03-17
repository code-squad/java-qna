package com.codessquad.qna.controller;

import com.codessquad.qna.util.Paths;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String showQuestionList() {
        return Paths.HOME_TEMPLATE;
    }
}
