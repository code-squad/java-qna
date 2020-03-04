package com.codessquad.qna.utils;

import com.codessquad.qna.constants.CommonConstants;
import com.codessquad.qna.user.User;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class HttpSessionUtils {

    private HttpSessionUtils() {}

    /**
     * Session에 담겨있는 User를 Optional로 Wrapping해서 반환합니다.
     *
     * @param session HttpSession 클래스
     * @return User object wrapped by Optional
     */
    public static Optional<User> getUserFromSession(HttpSession session) {
        return Optional.ofNullable((User) session.getAttribute(CommonConstants.SESSION_LOGIN_USER));
    }

    public static void sessionLogout(HttpSession session) {
        session.removeAttribute(CommonConstants.SESSION_LOGIN_USER);
        session.invalidate();
    }
}
