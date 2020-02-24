package com.codessquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private List<User> users = new ArrayList<>();

    @GetMapping("/form")
    public String createForm() {
        return "user/form";
    }

    @PostMapping("/create")
    public String createUser(User user) {
        users.add(user);
        return "redirect:/users";
    }

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/{userId}")
    public String showUserProfile(@PathVariable String userId, Model model) {
        for (User user : users) {
            if(userId.equals(user.getUserId())) {
                model.addAttribute("user", user);
                return "user/profile";
            }
        }
        return "redirect:/error/notFound.html";
    }
}