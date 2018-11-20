package codesquad;

import codesquad.user.User;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "loggedInUser";

    public static boolean isLoggedInUser(HttpSession session) {
        return session.getAttribute(USER_SESSION_KEY) != null;
    }

    public static User getUserFromSession(HttpSession session) {
        if(!isLoggedInUser(session)) {
            return null;
        }

        return (User)session.getAttribute(USER_SESSION_KEY);
    }
}