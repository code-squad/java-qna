package com.codessquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @PostMapping("/user/create")
    public String Create(User user, Model model) {
        System.out.println(user.toString());
        model.addAttribute("userId",user.getUserId());
        model.addAttribute("password",user.getPassword());
        model.addAttribute("name",user.getName());
        model.addAttribute("email",user.getEmail());
        return "CreateUser";
    }
}
