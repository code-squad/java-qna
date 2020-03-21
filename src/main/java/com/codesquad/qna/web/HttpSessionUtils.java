package com.codesquad.qna.web;

import com.codesquad.qna.domain.User;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "sessionedUser";

//    public static boolean isNotLoggedIn(HttpSession session) {
//        Object loginUser = session.getAttribute(USER_SESSION_KEY);
//
//        return !Optional.ofNullable(loginUser).isPresent();
//    }

    public static User couldGetValidUserFromSession(HttpSession session) {
        Object loginUser = session.getAttribute(USER_SESSION_KEY);

        return Optional.ofNullable(loginUser).map(userFromSession -> (User)userFromSession).orElseThrow(UserNotPermittedException::new);
    }
}
