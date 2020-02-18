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
    public String getUserForm() {
        return "user-form";
    }

    @PostMapping("/user/create")
    public String create(User user) {
        System.out.println("user => " + user);
        users.add(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String list(Model model) {
        model.addAttribute("users", users);
        return "list";
    }

    @GetMapping("/user/{userId}")
    public String viewProfile (@PathVariable("userId") String userId, Model model) {
        for(User user : users) {
            if(user.getUserId().equals(userId)) {
                model.addAttribute("user", user);
                return "profile";
            }
        }
        return "profile";
    }
}
