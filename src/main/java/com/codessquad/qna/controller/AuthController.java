package com.codessquad.qna.controller;

import com.codessquad.qna.repository.User;
import com.codessquad.qna.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        Optional<User> user = userRepository.findByUserId(userId);
        if (!user.isPresent()) {
            return "error/user/login_failed";
        }
        if (!password.equals(user.get().getPassword())) {
            return "error/user/login_failed";
        }
        session.setAttribute("authorizedUser", user.get());
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("authorizedUser");
        return "redirect:/";
    }
}
