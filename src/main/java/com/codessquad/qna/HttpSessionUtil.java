package com.codessquad.qna;

import javax.servlet.http.HttpSession;

public class HttpSessionUtil {
    public static final String USER_SESSION_KEY = "loggedInUser";

    ///로그인 유무를 판단하는 메소드
    public static boolean isLoginUser(HttpSession session) {
        Object loggedInUser = session.getAttribute(USER_SESSION_KEY);
        if (loggedInUser == null ) {
            return false;
        }
        return true;
    }

    public static User getUserFromSession(HttpSession session) {
        if (!isLoginUser(session)) {
            return null;
        }
        return (User)session.getAttribute((USER_SESSION_KEY));
    }
}
