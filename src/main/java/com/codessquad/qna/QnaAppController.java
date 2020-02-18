package com.codessquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QnaAppController {
    private List<User> users = new ArrayList<>();

    @PostMapping("/user/create")
    public String create(User user) {
        users.add(user);
        return "redirect:/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("users", users);
        return "list";
    }

    @GetMapping("/profile/{userId}")
    public String profile(@PathVariable String userId, Model model) {
        User user = users.stream()
                .filter(user1 -> user1.getUserId().equals(userId))
                .findAny()
                .orElse(null);
        model.addAttribute("user", user);
        return "profile";
    }
}
