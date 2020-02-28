package com.codessquad.qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    List<User> users = new ArrayList<>();

    @Autowired
    private UserRepository userRepository;

    @PostMapping("")
    public String createUser(User user) {
        userRepository.save(user);

        return "redirect:/users";
    }

    @GetMapping("")
    public String showUserList(Model model) {
        model.addAttribute("users", userRepository.findAll());

        return "user/list";
    }

    @GetMapping("/{userId}")
    public String showUserProfile(@PathVariable String userId, Model model) {
        users.stream()
                .filter(user -> user.getUserId().equals(userId))
                .forEach(user -> model.addAttribute("userprofile", user));

        return "user/profile";
    }

    @GetMapping("/{userId}/form")
    public String modifyUserProfile(@PathVariable String userId, Model model) {
        users.stream()
                .filter(user -> user.getUserId().equals(userId))
                .forEach(user -> model.addAttribute("userprofile", user));

        return "user/updateForm";
    }

    @PostMapping("/{userId}/update")
    public String updateUserProfile(@PathVariable String userId, Model model, User updateuser) {
        users.stream()
                .filter(user -> user.getUserId().equals(userId))
                .forEach(user -> {
                    user.setPassword(updateuser.getPassword());
                    user.setName(updateuser.getName());
                    user.setEmail(updateuser.getEmail());
                    model.addAttribute("userprofile", user);
                });

        return "redirect:/users";
    }
}
