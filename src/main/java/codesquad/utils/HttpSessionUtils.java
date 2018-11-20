package codesquad.utils;

import codesquad.user.User;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "loginUser";

    public static boolean isLoginUser(HttpSession session) {
        Object loginUser = session.getAttribute(USER_SESSION_KEY);
        return !(loginUser == null);
    }

    public static User getUserFromSession(HttpSession session){
        return (User)session.getAttribute(USER_SESSION_KEY);
    }
}
