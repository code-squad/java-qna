package com.codessquad.qna.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class UserController {
    private List<User> users = Collections.synchronizedList(new ArrayList<>());

    @GetMapping("/users")
    public String userList(Model model) {
        model.addAttribute("users", users);
        return "/user/list";
    }

    @PostMapping("/users")
    public String createUser(User user) {
        users.add(user);
        return "redirect:/users";
    }

    @GetMapping("/users/{userId}/form")
    public String updateUser(@PathVariable String userId, Model model) {
        model.addAttribute("userId", userId);
        return "/user/updateForm";
    }

    @PostMapping("/users/{userId}/update")
    public String updateUser(@PathVariable String userId, String password, String name, String email) {
        for(User user: users) {
            if(user.getUserId().equals(userId)) {
                user.setPassword(password);
                user.setName(name);
                user.setEmail(email);
                break;
            }
        }
        return "redirect:/users";
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
}
