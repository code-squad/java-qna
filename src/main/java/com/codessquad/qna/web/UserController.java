package com.codessquad.qna.web;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("")
    public String listPage(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "users/list";
    }

    @GetMapping("/createForm")
    public String createFormPage() {
        return "users/createForm";
    }

    @GetMapping("/{id}")
    public String showDetailPage(@PathVariable Long id, Model model) {
        model.addAttribute("user", userRepository.getOne(id));
        return "users/show";
    }

    @GetMapping("/{id}/updateForm")
    public String updateFormPage(@PathVariable Long id, Model model) {
        model.addAttribute("user", userRepository.getOne(id));
        return "users/updateForm";
    }

    @PostMapping("")
    public String createUser(@ModelAttribute User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @PatchMapping("/{id}")
    public String updateUser(@PathVariable Long id, User newUser) {
        userRepository.save(userRepository.getOne(id).merge(newUser));
        return "redirect:/users";
    }
}
