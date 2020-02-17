package com.codessquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QnaAppController {

    @GetMapping("/hello")
    public String hello(Model model) {
        System.out.println("HI");
        return "hello";
    }
}
