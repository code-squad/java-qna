package com.codessquad.qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;


@Controller
public class LogInController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/loginForm")
    public String loginPage() {
        return "loginForm";
    }

    @PostMapping("/user/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userRepository.findByUserId(userId);

        if (user == null) {
            System.out.println("no user exist");
            return "redirect:/user/loginForm";
        }

        if(password.equals(user.getPassword())) {
            System.out.println("login success");
            return  "redirect:/";
        }

        session.setAttribute("user", user);

        return "redirect:/";
    }

}
