package com.codessquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @PostMapping("/user/create")
    public String addUser(Model model, User user) {
        System.out.println("userId : " + user.getUserId() + " password : " + user.getPassword());
        model.addAttribute("name", user.getName());
        return "createok"; //templates의 index.html 호출
    }
}
