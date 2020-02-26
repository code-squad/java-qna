package com.codessquad.qna.controller;

import com.codessquad.qna.repository.User;
import com.codessquad.qna.repository.UserRepository;
import com.codessquad.qna.util.HttpSessionUtil;
import com.codessquad.qna.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
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
            return PathUtil.LOGIN_FAILED_TEMPLATE;
        }
        if (!password.equals(user.get().getPassword())) {
            return PathUtil.LOGIN_FAILED_TEMPLATE;
        }
        session.setAttribute(HttpSessionUtil.USER_SESSION_KEY, user.get());
        return PathUtil.HOME;
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(HttpSessionUtil.USER_SESSION_KEY);
        return PathUtil.HOME;
    }
}
