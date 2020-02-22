package com.codessquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private List<User> users = new ArrayList<>();

    @GetMapping("/form")
    public String createUserForm() {
        return "/user/form";
    }

    @GetMapping("/login")
    public String userLoginForm() {
        return "/user/login";
    }

    @PostMapping("/create")
    public String createUser(User user, Model model) {
        users.add(user);
        return "redirect:/users";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", users);
        return "/user/list";
    }

    @GetMapping("/{userId}")
    public String getUserInfoByUserId(@PathVariable String userId, Model model) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                model.addAttribute("name", user.getName());
                model.addAttribute("email", user.getEmail());
            }
        }
        return "/user/profile";
    }

    @GetMapping("/changeUserInfoLogin")
    public String changeUserInfo() {
        return "/user/changeUserInfoLogin";
    }

    @PostMapping("/changeUserInfoLogin")
    public String changeUserInfoLogin(String userId, String password, Model model) {
        model.addAttribute("userId", userId);
        for (User user : users) {
            if (user.getUserId().equals(userId) && user.getPassword().equals(password)) {
                return "user/updateForm";
            }
        }
        return "/user/login_failed";
    }

    @PostMapping("/{userId}/update")
    public String changeUserInfoForm(@PathVariable("userId") String userId, String password, String name, String email) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                if (password.length() > 0) {
                    user.setPassword(password);
                }
                if (name.length() > 0) {
                    user.setName(name);
                }
                if (email.length() > 0) {
                    user.setEmail(email);
                }
            }
        }
        return "redirect:/users";
    }
}
