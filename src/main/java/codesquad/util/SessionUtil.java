package codesquad.util;

import codesquad.exception.UserIdNotMatchException;
import codesquad.user.User;

import javax.servlet.http.HttpSession;

public class SessionUtil {
    public static final String USER_SESSION_KEY = "loginUser";

    public static User setUserToSession(HttpSession session, User user) {
        session.setAttribute(USER_SESSION_KEY, user);
        return user;
    }

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

    public static boolean permissionCheck(HttpSession session, User user) {
        if (SessionUtil.isLoginUser(session)) {
            matchId(session, user);
            return true;
        }
        return false;
    }

    public static void matchId(HttpSession session, User user) {
        if (!SessionUtil.getUserFromSesssion(session).matchId(user))
            throw new UserIdNotMatchException("USER_ID IS NOT CORRECT");
    }

}
