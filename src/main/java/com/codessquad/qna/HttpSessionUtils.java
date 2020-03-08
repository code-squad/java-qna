package com.codessquad.qna;

import com.codessquad.qna.domain.User;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String USER_SESSION_ID = "sessionedUser";

    public static User getUserFromSession(HttpSession httpSession) {
        return (User) httpSession.getAttribute(USER_SESSION_ID);
    }

    public static boolean isLoginUser(User sessionedUser) {
        return sessionedUser != null;
    }
}
