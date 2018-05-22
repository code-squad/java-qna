package codesquad.web;

import codesquad.domain.User;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    static final String USER_SESSION_KEY = "sessionedUser";

    static boolean isLoginUser(HttpSession session) {
        return session.getAttribute(USER_SESSION_KEY) != null;
    }

    public static User getUserFromSession(HttpSession session) {
        if (!isLoginUser(session)) {
            return null;
        }

        return (User) session.getAttribute(USER_SESSION_KEY);
    }
}
