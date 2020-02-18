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
    public List<User> users = new ArrayList<>();

    @GetMapping("/index.html")
    public String index() {
        return "index";
    }

    @PostMapping("/user/create")
    public String create(User user) {
        System.out.println("user : " + user);
        users.add(user);
        return "redirect:user/list.html";
    }

    @GetMapping("/user/list.html")
    public String list(Model model) {
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/user/profile.html/{userId}")
    public String profile(Model model, @PathVariable String userId) {

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId().equals(userId)) {
                model.addAttribute("userId", userId);
                model.addAttribute("email", users.get(i).getEmail());
                return "user/profile";
            }
        }
        return "user/profile";
    }
}
