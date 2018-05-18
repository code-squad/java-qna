package codesquad;

import codesquad.model.User;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String HTTP_SESSION_KEY = "sessionUser";

    public static boolean userIsLoggedIn(HttpSession session) {
        return session.getAttribute(HTTP_SESSION_KEY) != null;
    }

    public static User getUserFromSession(HttpSession session) {
        return (User)session.getAttribute(HTTP_SESSION_KEY);
    }
}
