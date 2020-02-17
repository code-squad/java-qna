package com.codessquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    List<User> Users = new ArrayList<>();

    @PostMapping("/user/create")
    public String addUser(Model model, User user) {
        System.out.println("userId : " + user.getUserId() + " password : " + user.getPassword());
        model.addAttribute("name", user.getName());
        Users.add(user);
        return "createok"; //templates의 index.html 호출
    }
}
