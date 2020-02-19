package com.codessquad.qna.controller;

import com.codessquad.qna.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private List<User> users = new ArrayList<>();

    @GetMapping
    public String showUserList(Model model) {
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/{userId}")
    public String getUserProfile(@PathVariable String userId, Model model) {
        for (User user: users) {
            if(user.getUserId().equals(userId)) {
                model.addAttribute("user", user);
                return "user/profile";
            }
        }
        return "/";
    }

    @GetMapping("/{id}/edit")
    public String updateUser(@PathVariable int id, Model model) {
        User user = users.get(id);
        model.addAttribute("user", user);
        model.addAttribute("id", id);
        return "user/edit";
    }

    @PostMapping
    public String createUser(User user) {
        users.add(user);
        return "redirect:/users";
    }
}
