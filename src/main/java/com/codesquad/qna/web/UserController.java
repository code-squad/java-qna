package com.codesquad.qna.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.ArrayList;

@Controller
public class UserController {
    List<User> users = new ArrayList<>();

    @PostMapping("/user/create")
    public String createUser(User user) {
        System.out.println(user);
        users.add(user);

        return "redirect:/users";
    }

    @GetMapping("/users")
    public String printUsers(Model model) {
        model.addAttribute("users", users);

        return "list";
    }
}
