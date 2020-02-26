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
@RequestMapping("/user")
public class UserController {
    private List<User> users = new ArrayList<>();

    @GetMapping("/form")
    public String createForm() {
        return "user/form";
    }

    @PostMapping("/create")
    public String create(User user) {
        users.add(user);
        return "redirect:/user";
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/{id}")
    public String read(@PathVariable String id, Model model) {
        for (User user : users) {
            if(id.equals(user.getId())) {
                model.addAttribute("user", user);
                return "user/profile";
            }
        }
        return "/error/notFound.html";
    }
}
