package codesquad.web;

import codesquad.domain.User;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    // 상수값을 뽑는 자바의 컨벤션
    public static final String USER_SESSION_KEY = "sessionedUser";

    public static boolean isLoginUser(HttpSession session) {
        Object sessionedUser = session.getAttribute(USER_SESSION_KEY);
        if (sessionedUser == null)
            return false;
        return true;
    }

    public static User getUserFromSession(HttpSession session) {
        if (!isLoginUser(session))
            return null;
        return (User)session.getAttribute(USER_SESSION_KEY);
    }
}
