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
        return "redirect:/user/list";
    }

    @GetMapping("/user/list")
    public String list(Model model) {
        model.addAttribute("users", users);
        return "/user/list";
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
    public String getUserInfoByUserId(@PathVariable String userId, Model model) {
        for (User user : users) {
            if(user.getUserId().equals(userId)){
                model.addAttribute("name",user.getName());
                model.addAttribute("email",user.getEmail());
            }
        }
        return "/user/profile";
    }
}
