package com.codesquad.qna.web;

import com.codesquad.qna.domain.User;
import com.codesquad.qna.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.ArrayList;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/users")
    public String createUser(User user) {
        userRepository.save(user);

        return "redirect:/users";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());

        return "user/list";
    }

    @GetMapping("/users/{id}")
    public String showUser(@PathVariable Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).orElseThrow(UserNotFoundException::new));

        return "user/profile";
    }

    @GetMapping("/users/{id}/form")
    public String userForm(@PathVariable Long id, Model model) {
        System.out.println(userRepository.findById(id));
        model.addAttribute("user", userRepository.findById(id).orElseThrow(UserNotFoundException::new));

        return "user/updateForm";
    }

    @PutMapping("/users/{id}")
    public String updateUser(@PathVariable long id, User updatedUser) {
        User selectedUser = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        selectedUser.update(updatedUser);
        userRepository.save(selectedUser);

        return "redirect:/users";
    }
}
