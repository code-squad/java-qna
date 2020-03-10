package com.codessquad.qna.web;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.domain.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private Logger logger = LogManager.getLogger(UserController.class);

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
    public String detailPage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userRepository.getOne(id));
        return "users/detail";
    }

    @GetMapping("/{id}/updateForm")
    public String updateFormPage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userRepository.getOne(id));
        return "users/updateForm";
    }

    @PostMapping("")
    public String createUser(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable("id") Long id, User newUser) {
        userRepository.save(userRepository.getOne(id).merge(newUser));
        return "redirect:/users";
    }
}
