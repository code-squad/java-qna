package com.codessquad.qna.controller;

import com.codessquad.qna.exception.CustomNoSuchUserException;
import com.codessquad.qna.repository.User;
import com.codessquad.qna.repository.UserRepository;
import com.codessquad.qna.util.ErrorMessageUtil;
import com.codessquad.qna.util.HttpSessionUtil;
import com.codessquad.qna.util.PathUtil;
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
                new CustomNoSuchUserException(PathUtil.LOGIN_FAILED_TEMPLATE, ErrorMessageUtil.NOTFOUND_USER));
        if (!user.isCorrectPassword(password)) {
            return PathUtil.LOGIN_FAILED_TEMPLATE;
        }
        session.setAttribute(HttpSessionUtil.USER_SESSION_KEY, user);
        return PathUtil.HOME;
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(HttpSessionUtil.USER_SESSION_KEY);
        return PathUtil.HOME;
    }
}
