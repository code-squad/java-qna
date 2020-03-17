package com.codesquad.qna.web;

import com.codesquad.qna.domain.User;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "loginUser";

    // session에 user가 있는지 없는지 여부를 확인하게 하였습니다.
    public static boolean isLoginUser(HttpSession session) {
        Object loginUser = session.getAttribute(USER_SESSION_KEY);
        return !(loginUser == null);
    }

    // session에 저장되어있는 user값을 optional로 반환하도록 하였습니다.
    public static Optional<User> getUserFromSession(HttpSession session) {
        Optional<User> sessionUser = Optional.empty();
        if (isLoginUser(session)) {
            sessionUser = Optional.of((User) session.getAttribute(USER_SESSION_KEY));
        }
        return sessionUser;
    }
}
