package com.codessquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @PostMapping("/user/create")
    public String Create(String userId, String password, String name, String email, Model model) {
        model.addAttribute("userId",userId);
        model.addAttribute("password",password);
        model.addAttribute("name",name);
        model.addAttribute("email",email);
        return "CreateUser";
    }
}
