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
            return "redirect:/user/login/failed";
        }

        if (!user.matchPassword(password ) || !user.matchId(userId)) {
            System.out.println("login failed");
            session.setAttribute(HttpSessionUtil.USER_SESSION_KEY, user);
            System.out.println("incorrect id or password");
            return "redirect:/user/login/failed";
        }

        if (user.matchPassword(password)) {
            System.out.println("login success");
            session.setAttribute(HttpSessionUtil.USER_SESSION_KEY, user);
            System.out.println("attribute 'loginUser' added to session");
        }
        return "redirect:/posts";
    }

    @GetMapping("/user/login/failed")
    public String showFailPage() {
        return "login_failed";
    }



    @GetMapping("/user/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("loggedInUser");
        System.out.println("loginUser logout");
        return "redirect:/";
    }

}
