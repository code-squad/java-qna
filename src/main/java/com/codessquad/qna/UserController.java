package com.codessquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    List<User> users = new ArrayList<>();

    @PostMapping("/user/create.html")
    public String create(User user, Model model) {
        users.add(user);
        return "redirect:/user/list.html";
    }

    @GetMapping("/user/list.html")
    public String list(Model model) {
        model.addAttribute("users", users);
        return "/user/list";
    }
}
