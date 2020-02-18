package com.codessquad.qna.web;

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

    @GetMapping("/users")
    public String userList(Model model) {
        model.addAttribute("users", users);
        return "/user/list";
    }

    @GetMapping("/users/{userId}")
    public String userProfile(@PathVariable String userId, Model model) {
        User target = null;
        for(User user: users) {
            if(user.getUserId().equals(userId)) {
                target = user;
                break;
            }
        }
        model.addAttribute("user", target);
        return "/user/profile";
    }

    @PostMapping("/users")
    public String createUser(User user) {
        users.add(user);
        return "redirect:/users";
    }
}
