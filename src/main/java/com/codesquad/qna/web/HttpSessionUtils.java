package com.codesquad.qna.web;

import com.codesquad.qna.domain.User;
import com.codesquad.qna.exception.exception.UnauthorizedException;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "sessionedUser";

    private HttpSessionUtils() {
        throw new IllegalStateException("Utility Class");
    }

    public static User getUserFromSession(HttpSession session) {
        return (User) session.getAttribute(USER_SESSION_KEY);
    }

    public static void checkLogin(HttpSession session) {
        if (getUserFromSession(session) == null) {
            throw UnauthorizedException.notLogin();
        }
    }
}
