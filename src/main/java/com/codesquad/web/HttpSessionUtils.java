package com.codesquad.web;

import com.codessquad.qna.domain.User;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "sessionedUser";

    public static boolean isUserLogin(HttpSession session) {
        Object sessionedUser = session.getAttribute(USER_SESSION_KEY);
        return sessionedUser != null;
    }

    public static User getUserFromSession(HttpSession session) {
        if (!isUserLogin(session)) {
            return null;
        }
        return (User) session.getAttribute(USER_SESSION_KEY);
    }
}
