package com.codessquad.qna.web;

import com.codessquad.qna.domain.User;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "sessionedUser";

    public static boolean isLoggedInUser(HttpSession session) {
        User sessionedUser = (User) session.getAttribute(USER_SESSION_KEY);
        return sessionedUser != null;
    }

    public static User getUserFromSession(HttpSession session) {
        if (!isLoggedInUser(session)) {
            return null;
        }
        return (User) session.getAttribute(USER_SESSION_KEY);
    }
}
