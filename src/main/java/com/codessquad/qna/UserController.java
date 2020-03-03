package com.codessquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private List<User> users = new ArrayList<>();


    @GetMapping("/user/create")
    public String createUser(User user, Model model) {
        System.out.println( user);
        users.add(user);
        model.addAttribute("users", users);
        System.out.println();
        return "userList";
    }

}
