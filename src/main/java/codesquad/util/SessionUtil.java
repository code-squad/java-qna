package codesquad.util;

import codesquad.user.User;

import javax.servlet.http.HttpSession;

public class SessionUtil {
    public static final String USER_SESSION_KEY = "loginUser";

    public static boolean isLoginUser(HttpSession session) {
        Object loginUser = session.getAttribute(USER_SESSION_KEY);

        return !(loginUser == null);
    }

    public static User getUserFromSesssion(HttpSession session) {
        if (!isLoginUser(session)) {
            return null;
        }
        return (User) session.getAttribute(USER_SESSION_KEY);
    }

    public static boolean permissionCheck(HttpSession session, Long id) {
        if (SessionUtil.isLoginUser(session)) {
            matchId(session, id);
            return true;
        }
        return false;
    }

    public static void matchId(HttpSession session, Long id) {
        if (!SessionUtil.getUserFromSesssion(session).matchId(id))
            throw new IllegalArgumentException("Your not allow this page");
    }

}
