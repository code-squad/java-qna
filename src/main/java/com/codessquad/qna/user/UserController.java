package com.codessquad.qna.user;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private List<User> users = new ArrayList<>();

    @GetMapping("/form")
    public String form() {
        return "user/form";
    }

    @PostMapping("/create")
    public String create(User user) {
        System.out.println("User: " + user);
        users.add(user);
        return "redirect:/users/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/{userId}")
    public String profile(@PathVariable String userId, Model model) {
        model.addAttribute("user", checkUser(userId));
        return "/user/profile";
    }

    private User checkUser(String userId) {
        for (User user : users) {
            if (userId.equals(user.getUserId()))
                return user;
        }
        return null;
    }
}
