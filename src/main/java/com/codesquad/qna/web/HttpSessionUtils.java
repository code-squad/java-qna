package com.codesquad.qna.web;

import com.codesquad.qna.domain.User;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "loginUser";

    public static boolean isLoginUser(HttpSession session) {
        Object loginUser = session.getAttribute(USER_SESSION_KEY);
        return !(loginUser == null);
    }

    public static Optional<User> getUserFromSession(HttpSession session) {
        Optional<User> sessionUser = Optional.empty();
        if (isLoginUser(session)) {
            sessionUser = Optional.of((User) session.getAttribute(USER_SESSION_KEY));
        }
        return sessionUser;
    }
}
