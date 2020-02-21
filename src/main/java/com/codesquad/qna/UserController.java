package com.codesquad.qna;

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

    @GetMapping("/user/login")
    public String login() {
        return "/user/login";
    }

    @GetMapping("/user/join")
    public String moveUserForm() {
        return "/user/join";
    }

    @PostMapping("/user/create")
    public String addUser(User user) {
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
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                model.addAttribute("currentUser", user);
                break;
            }
        }

        return "/user/profile";
    }

}
