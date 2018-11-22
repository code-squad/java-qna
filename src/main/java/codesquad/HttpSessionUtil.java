package codesquad;

import codesquad.user.User;

import javax.servlet.http.HttpSession;

public class HttpSessionUtil {
    public static final String USER_SESSION_KEY = "sessionedUser";

    public static boolean isLoginUser(HttpSession session) {
        Object sessionedUser = session.getAttribute(USER_SESSION_KEY);
        return sessionedUser != null;
    }

    public static User getUserFromSession(HttpSession session) {
        if (!isLoginUser(session)) {
            return null;
        }
        return (User) session.getAttribute(USER_SESSION_KEY);
    }
}
