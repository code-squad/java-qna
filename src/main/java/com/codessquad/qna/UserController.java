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
@RequestMapping("/users/*")
public class UserController {
    List<User> users = new ArrayList<>();

    @PostMapping("/create")
    public String create(User user) {
        users.add(user);
        System.out.println(users);
        return "redirect:/users/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("users", users);
        return "/users/list";
    }

    @GetMapping("/{userId}")
    public String profile(@PathVariable("userId") String userId, Model model) {
        User matchedUser = null;
        for(User user: users) {
            if(user.userId.equals(userId)) {
                matchedUser = user;
                break;
            }
        }
        model.addAttribute("user", matchedUser);
        return "/users/profile";
    }

    @GetMapping("/join")
    public String join() {
        return "/users/form";
    }
}
