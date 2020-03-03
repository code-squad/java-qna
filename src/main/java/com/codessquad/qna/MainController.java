package com.codessquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("")
    public String main() {
        return "main";
    }

    @GetMapping("/main")
    public String backToMain() {
        return "main";
    }

    @GetMapping("/posts")
    public String showPosts() {
        return "index";
    }
}
