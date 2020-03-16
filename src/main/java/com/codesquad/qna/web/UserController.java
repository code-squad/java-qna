package com.codesquad.qna.web;

import com.codesquad.qna.domain.User;
import com.codesquad.qna.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users/login-form")
    public String loginForm() {
        return "user/login";
    }

    @PostMapping("/users/login")
    public String login(String userId, String password, HttpSession session) {
        User selectedUser = userRepository.findByUserId(userId);

        if (selectedUser == null) {
            return "redirect:/users/login-form";
        }

        if (!selectedUser.isCorrectPassword(password)) {
            System.out.println("Incorrect");
            return "redirect:/users/login-form";
        }
        session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, selectedUser);
        return "redirect:/";
    }

    @GetMapping("/users/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);

        return "redirect:/";
    }

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
    public String userForm(@PathVariable Long id, Model model, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login-form";
        }

        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        User selectedUser = userRepository.findById(sessionedUser.getId()).orElseThrow(UserNotFoundException::new);
        model.addAttribute("user", selectedUser);

        return "user/updateForm";
    }

    @PutMapping("/users/{id}")
    public String updateUser(@PathVariable Long id, @RequestParam String confirmPassword,  User updatedUser) {
        User selectedUser = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        if (selectedUser.isCorrectPassword(confirmPassword)) {
            selectedUser.update(updatedUser);
            userRepository.save(selectedUser);
            return "redirect:/users";
        } else {
            return "redirect:/users/{id}/form";
        }
    }
}
