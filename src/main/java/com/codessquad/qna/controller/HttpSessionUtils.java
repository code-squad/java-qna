package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "sessionUser";

    public static boolean isLoginUser(HttpSession session) {
        Object sessionUser = session.getAttribute(USER_SESSION_KEY);
        return sessionUser != null;
    }

    public static User getUserFromSession(HttpSession session) {
        //이 메소드에서 isLoginUser()를 쓴다면 Controller에서는 isLoginUser()를 사용하지 않아도 되는거 아닌가?
        //그럼 반환타입을 Optional로 하고 orElseThrow에서 사용자 정의 익셉션 던져서 익셉션 핸들러에서 로그인 페이지로 보내도 될 것 같은데
        if (!isLoginUser(session)) {
            return null;
        }
        return (User)session.getAttribute(USER_SESSION_KEY);
    }
}
