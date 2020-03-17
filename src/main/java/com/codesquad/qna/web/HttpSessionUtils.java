package com.codesquad.qna.web;

import com.codesquad.qna.advice.exception.UnauthorizedException;
import com.codesquad.qna.domain.User;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "loginUser";

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
