package codesquad.util;

import codesquad.user.User;

import javax.servlet.http.HttpSession;

public class SessionUtil {
    public static final String USER_SESSION_KEY = "sessionedUser";

    public static boolean isSessionedUser(HttpSession session) {
        return session.getAttribute(USER_SESSION_KEY) != null;
    }

    public static User getUserFromSession(HttpSession session) {
        if (!isSessionedUser(session)) return null;
        return (User)session.getAttribute(USER_SESSION_KEY);
    }
}
