package codesquad;

import codesquad.exceptions.NoSessionedUserException;
import codesquad.model.User;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String HTTP_SESSION_KEY = "sessionUser";

    public static boolean userIsLoggedIn(HttpSession session) {
        return session.getAttribute(HTTP_SESSION_KEY) != null;
    }

    public static User getUserFromSession(HttpSession session) throws NoSessionedUserException {
        if (!userIsLoggedIn(session)) {
            throw new NoSessionedUserException("Http.session.nonexistent");
        }
        return (User)session.getAttribute(HTTP_SESSION_KEY);
    }

    public static void endSession(HttpSession session) {
        if (!userIsLoggedIn(session)) {
            throw new NoSessionedUserException();
        }
        session.removeAttribute(HTTP_SESSION_KEY);
    }
}
