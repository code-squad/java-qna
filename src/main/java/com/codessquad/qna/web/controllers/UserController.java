package com.codessquad.qna.web.controllers;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.web.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String listPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users/list";
    }

    @GetMapping("/createForm")
    public String createFormPage() {
        return "users/createForm";
    }

    @GetMapping("/{id}")
    public String detailPage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "users/detail";
    }

    @GetMapping("/{id}/updateForm")
    public String updateFormPage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "users/updateForm";
    }

    @PostMapping("")
    public String createUser(User newUser) {
        userService.register(newUser);
        return "redirect:/users";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable("id") Long id, User newUser) {
        User targetUser = userService.getUserById(id);
        userService.register(targetUser.merge(newUser));
        return "redirect:/users";
    }
}
