package codesquad.util;

import codesquad.user.User;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "loginUser";

    // did not login.
    public static boolean existLoginUserFromSession(HttpSession session){
        return session.getAttribute(HttpSessionUtils.USER_SESSION_KEY) != null;
    }

    public static User getLoginUserFromSession(HttpSession session){
        if(!existLoginUserFromSession(session)) new IllegalArgumentException("empty session");
        return (User) session.getAttribute(USER_SESSION_KEY);
    }
}
