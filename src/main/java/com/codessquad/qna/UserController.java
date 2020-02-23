package com.codessquad.qna;

import com.codessquad.qna.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    public List<User> users = new ArrayList<>();

    @PostMapping("/user/create")
    public String create(User user) {
        System.out.println("user : " + user);
        users.add(user);
        return "redirect:/user/list";
    }

    @GetMapping("/user/list")
    public String list(Model model) {
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/user/profile/{userId}")
    public String profile(Model model, @PathVariable String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                model.addAttribute("userId", userId);
                model.addAttribute("email", user.getEmail());
                return "user/profile";
            }
        }
        return "user/profile";
    }
}
