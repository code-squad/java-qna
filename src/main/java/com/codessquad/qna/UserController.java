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
    private List<User> users = new ArrayList<>();

    @GetMapping("/user/form")
    public String viewUserForm() {
        return "/user/form";
    }

    @PostMapping("/user/create")
    public String createUser(User user) {
        user.setIndex(users.size() + 1);
        System.out.println("user => " + user);
        users.add(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String viewList(Model model) {
        model.addAttribute("users", users);
        return "/user/list";
    }

    @GetMapping("/user/{userId}")
    public String viewProfile(@PathVariable("userId") String userId, Model model) {
        for(User user : users) {
            if(user.getUserId().equals(userId)) {
                model.addAttribute("user", user);
                return "/user/profile";
            }
        }
        return "/user/profile";
    }

    @GetMapping("/user/{userId}/form")
    public String viewUpdateForm(@PathVariable("userId") String userId, Model model) {
        for(User user : users) {
            if(user.getUserId().equals(userId)) {
                model.addAttribute("user", user);
                return "/user/updateForm";
            }
        }
        return "/user/updateForm";
    }

    @PostMapping("/user/{userId}/update")
    public String viewUpdatedList(@PathVariable String userId, String password, String name, String email) {
        for(User user : users) {
            if(user.getUserId().equals(userId) && user.getPassword().equals(password)) {
                user.setName(name);
                user.setEmail(email);
                return "redirect:/users";
            }
        }
        return "redirect:/users";
    }
}
