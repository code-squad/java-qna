package com.codessquad.qna.web.controllers;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.web.services.AuthService;
import com.codessquad.qna.web.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/users")
public class UserController {
    private final AuthService authService;
    private final UserService userService;

    public UserController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @GetMapping
    public String listPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users/list";
    }

    @GetMapping("/{id}")
    public String detailPage(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "users/detail";
    }

    @GetMapping("/createForm")
    public String createFormPage() {
        return "users/createForm";
    }

    @GetMapping("/{id}/updateForm")
    public String updateFormPage(HttpServletRequest request, @PathVariable("id") Long targetUserId, Model model) {
        User targetUser = userService.getUserById(targetUserId);
        authService.hasAuthorization(request, targetUser);
        model.addAttribute("user", targetUser);
        return "users/updateForm";
    }

    @PostMapping
    public String createUser(User newUser) {
        userService.register(newUser);
        return "redirect:/users";
    }

    @PutMapping("/{id}")
    public String updateUser(HttpServletRequest request, @PathVariable("id") Long targetUserId, User newUser) {
        User targetUser = userService.getUserById(targetUserId);
        authService.hasAuthorization(request, targetUser);
        userService.edit(targetUser, newUser);
        return "redirect:/users";
    }
}
