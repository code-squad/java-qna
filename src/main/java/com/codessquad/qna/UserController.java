package com.codessquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    List<User> users = new ArrayList<>();

    @PostMapping("/users")
    public String createUser(User user) {
        users.add(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String showUserList(Model model) {
        model.addAttribute("users", users);
        return "user/list";
    }
}
