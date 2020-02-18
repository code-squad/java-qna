package com.codessquad.qna.controller;

import com.codessquad.qna.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private List<User> userList = new ArrayList<>();

    @PostMapping("/user/create")
    public String createUser(User user) {
        userList.add(user);
        System.out.println(user);
        return "redirect:/user/list";
    }

    @GetMapping("/user/list")
    public String getUserList(Model model) {
        model.addAttribute("users", userList);
        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String getUserProfile(@PathVariable String userId, Model model) {
        model.addAttribute("user", getUserById(userId));
        return "user/profile";
    }

    public User getUserById(String userId) {
        for (User user : userList) {
            if(userId.equals(user.getUserId())){
                return user;
            }
        }
        return null;
    }

}
