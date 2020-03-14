package com.codessquad.qna.utils;

import com.codessquad.qna.user.User;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "sessionUser";

    public static boolean isNoneExistentUser(HttpSession session) {
        Object sessionUser = session.getAttribute(USER_SESSION_KEY);
        return sessionUser == null;
    }

    public static User getUserFromSession(HttpSession session) {
        if (isNoneExistentUser(session)) {
            return null;
        }

        return (User)session.getAttribute(USER_SESSION_KEY);
    }
}
