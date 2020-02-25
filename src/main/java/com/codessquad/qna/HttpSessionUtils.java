package com.codessquad.qna;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "sessionedUser";

    public static boolean isLogin(HttpSession session) {
        Object sessionedUser = session.getAttribute(USER_SESSION_KEY);
        if (sessionedUser == null) {
            return false;
        }
        return true;
    }

    public static User getUserFromSession(HttpSession session) {
        if (!isLogin(session)) {
            return null;
        }
        return (User)session.getAttribute(USER_SESSION_KEY);
    }
}
