package com.codessquad.qna.web.controllers;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.web.services.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping()
    public String loginPage() {
        return "auth/loginForm";
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request, User entrant) {
        authService.validateAuthentication(request, entrant);
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        authService.expireAuthentication(request, response);
        return "redirect:/";
    }
}
