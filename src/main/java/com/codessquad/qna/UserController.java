package com.codessquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private List<User> users = new ArrayList<>();

    @PostMapping("/user/create")
    public String signUp(User newUser) {
        users.add(newUser);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String listsOfUsers(Model model) {
        model.addAttribute("users", users);
        return "user/list";
    }
}
