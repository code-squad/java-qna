package codesquad.util;

import codesquad.domain.exception.UnAuthorizedException;
import codesquad.domain.user.User;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "sessionUser";

    public static boolean isLogin(HttpSession session) {
        return session.getAttribute(USER_SESSION_KEY) != null;
    }

    public static Optional<User> getUserFromSession(HttpSession session) {
        User user = (User) session.getAttribute(USER_SESSION_KEY);
        return Optional.of(user);
    }

    public static Optional<User> getUserFromSession(HttpSession session, Long pathId) {
        User sessionUser = getUserFromSession(session).get();
        if (!isMatchRequestUserAndSessionUser(sessionUser, pathId)) {
            throw new UnAuthorizedException("user.mismatch.sessionuser");
        }
        return Optional.of(sessionUser);
    }

    private static boolean isMatchRequestUserAndSessionUser(User sessionUser, Long pathId) {
        return sessionUser.isMatch(pathId);
    }

    public static void logout(HttpSession session) {
        session.removeAttribute(USER_SESSION_KEY);
    }
}
