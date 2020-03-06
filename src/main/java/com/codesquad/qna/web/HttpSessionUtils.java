package com.codesquad.qna.web;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "sessionedUser";

    private HttpSessionUtils() {
        throw new IllegalStateException("Utility Class");
    }

    public static Optional<Object> getObject(HttpSession session) {
        return Optional.ofNullable(session.getAttribute(USER_SESSION_KEY));
    }
}
