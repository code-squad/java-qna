package com.codessquad.qna;

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

    @GetMapping("/user/form")
    public String form() {
        return "user/form";
    }

    @PostMapping("/user/create")
    public String create(User user) {
        users.add(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String list(Model model) {
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String profile(@PathVariable String userId, Model model) {
        User userHaveUrlUserId = users.stream().filter(user -> user.getUserId().equals(userId)).findAny().orElse(null);
        model.addAttribute("user",userHaveUrlUserId);
        return "user/profile";
    }

    @GetMapping("/")
    public String main() {
        return "/index";
    }
}