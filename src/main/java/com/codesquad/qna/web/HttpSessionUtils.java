package com.codesquad.qna.web;

import com.codesquad.qna.domain.User;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "sessionedUser";

    private HttpSessionUtils() {
        throw new IllegalStateException("Utility Class");
    }

    public static Optional<User> getOptionalUser(HttpSession session) {
        return Optional.ofNullable((User) session.getAttribute(USER_SESSION_KEY));
    }
}
