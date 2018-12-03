package codesquad.config;

import codesquad.user.User;
import codesquad.user.UserNotFoundException;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public final static String USER_SESSION_KEY = "loginUser";

    public static boolean isLogin(HttpSession session) {
        if(session == null) {
            return false;
        }
        return session.getAttribute(USER_SESSION_KEY) != null;
    }

    public static User getUserFromSession(HttpSession session) {
        if (!isLogin(session)) {
            return null;
        }
        return (User) session.getAttribute(USER_SESSION_KEY);
    }

}
