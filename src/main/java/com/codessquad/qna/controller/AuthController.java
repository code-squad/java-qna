package com.codessquad.qna.controller;

import com.codessquad.qna.exception.NoSuchUserException;
import com.codessquad.qna.repository.User;
import com.codessquad.qna.repository.UserRepository;
import com.codessquad.qna.util.ErrorMessages;
import com.codessquad.qna.util.HttpSessionUtil;
import com.codessquad.qna.util.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpSession;

@Controller
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userRepository.findByUserId(userId).orElseThrow(() ->
                new NoSuchUserException(Paths.LOGIN_FAILED_TEMPLATE, ErrorMessages.NOTFOUND_USER));
        if (!user.isCorrectPassword(password)) {
            return Paths.LOGIN_FAILED_TEMPLATE;
        }
        session.setAttribute(HttpSessionUtil.USER_SESSION_KEY, user);
        return Paths.HOME;
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(HttpSessionUtil.USER_SESSION_KEY);
        return Paths.HOME;
    }
}
