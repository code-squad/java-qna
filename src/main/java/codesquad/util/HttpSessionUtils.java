package codesquad.util;

import codesquad.domain.user.User;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "sessionedUser";

    public static boolean isLogin(HttpSession session) {
        return session.getAttribute(USER_SESSION_KEY) != null;
    }

    public static Optional<User> getUserFromSession(HttpSession session, Long id) {
        if (!isLogin(session)) {
            return Optional.empty();
        }

        User user = (User) session.getAttribute(USER_SESSION_KEY);
        if (!user.isMatch(id)) {
            return Optional.empty();
        }
        return Optional.of(user);
    }
}
