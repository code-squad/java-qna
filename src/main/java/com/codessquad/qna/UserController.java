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
    List<User> users = new ArrayList<>();

    @GetMapping("/user/login")
    public String login() {
        return "/user/login";
    }

    @GetMapping("/user/join")
    public String moveUserForm() {
        return "/user/join";
    }

    @PostMapping("/user/create")
    public String addUser(Model model, User user) {
        System.out.println("userId : " + user.getUserId() + " password : " + user.getPassword());
        model.addAttribute("name", user.getName());
        users.add(user);
        return "redirect:/users"; //templates의 index.html 호출
    }

    @GetMapping("/users")
    public String viewUsers(Model model) {
        model.addAttribute("users", users);
        return "/user/list";
    }

    @GetMapping("/users/{userId}")
    public String viewProfile(Model model, @PathVariable String userId) {
        User currentUser = null;
        
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                currentUser = user;
                break;
            } 
        }
        
        model.addAttribute("currentUser", currentUser);
        return "/user/profile";
    }

}
