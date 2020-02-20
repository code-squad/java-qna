package com.codessquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    List<User> users = new ArrayList<>();

    @GetMapping("/user/form")
    public String createUserForm() {
        return "/user/form";
    }

    @GetMapping("/user/login")
    public String userLoginForm() {
        return "/user/login";
    }

    @PostMapping("/user/create")
    public String createUser(User user, Model model) {
        users.add(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String list(Model model) {
        model.addAttribute("users", users);
        return "/user/list";
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
    public String getUserInfoByUserId(@PathVariable String userId, Model model) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                model.addAttribute("name", user.getName());
                model.addAttribute("email", user.getEmail());
            }
        }
        return "/user/profile";
    }

    @RequestMapping(value = "/user/changeUserInfoLogin", method = RequestMethod.GET)
    public String changeUserInfo() {
        return "/user/changeUserInfoLogin";
    }

    @RequestMapping(value = "/user/changeUserInfoLogin", method = RequestMethod.POST)
    public String changeUserInfoLogin(String userId, String password, Model model) {
        model.addAttribute("userId", userId);
        for (User user : users) {
            if (user.getUserId().equals(userId) && user.getPassword().equals(password)) {
                return "user/updateForm";
            }
        }
        return "/user/login_failed";
    }

    @RequestMapping(value = "/users/{userId}/update", method = RequestMethod.POST)
    public String changeUserInfoForm(@PathVariable("userId") String userId,String password, String name, String email) {

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
