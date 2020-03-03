package com.codessquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
    @GetMapping("/firstpage")
    public String welcomePage(String menu, Model model) {
        System.out.println("menu : " + menu);
        model.addAttribute("menu", menu);
        return "firstPage";
    }
}
