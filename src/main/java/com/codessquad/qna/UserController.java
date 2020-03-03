package com.codessquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @PostMapping("/user/create")
    public String createUser(User user, Model model) {
        System.out.println("userId : " + user);
        model.addAttribute("userInfo", user);
        return "firstPage";
    }
}
