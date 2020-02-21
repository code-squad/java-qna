package com.codessquad.qna.user;

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
    public String create(User user) {
        users.add(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String list(Model model) {
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/profile/{userId}")
    public String profile(@PathVariable String userId, Model model) {
        User user = users.stream()
                .filter(user1 -> user1.getUserId().equals(userId))
                .findAny()
                .orElse(null);
        model.addAttribute("user", user);
        return "user/profile";
    }
}
