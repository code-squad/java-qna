package codesquad.utils;

import codesquad.question.Question;
import codesquad.user.User;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "loginUser";

    public static boolean isLoginUser(HttpSession session) {
        Object loginUser = session.getAttribute(USER_SESSION_KEY);
        return !(loginUser == null);
    }

    public static User getUserFromSession(HttpSession session) {
        return (User) session.getAttribute(USER_SESSION_KEY);
    }

    public static boolean isValid(HttpSession session, Question question) {
        if (!isLoginUser(session)) {
            return false;
        }
        User loginUser = getUserFromSession(session);
        return question.matchUserId(loginUser);
    }
}
