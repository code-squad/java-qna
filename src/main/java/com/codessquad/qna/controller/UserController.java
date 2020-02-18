package com.codessquad.qna.controller;

import com.codessquad.qna.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private List<User> users = new ArrayList<>();

    @PostMapping("/users")
    public String createUser(User user) {
        users.add(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String getUserList(Model model) {
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/users/{userIndex}")
    public String getUserProfile(@PathVariable int userIndex, Model model) {
       User user = users.get(userIndex);
       model.addAttribute("user", user);
       return "user/profile";
    }
}
