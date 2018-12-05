package codesquad;

import codesquad.question.Question;
import codesquad.user.User;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "loginUser";

    public static boolean isLoginUser(HttpSession session) {
        if (session.getAttribute(USER_SESSION_KEY) == null) {
            return false;
        }
        return true;
    }

    public static User getUserFromSession(HttpSession session) {
        if (!isLoginUser(session)) {
            throw new IllegalArgumentException("로그인 해주세요!!");
        }
        return (User) session.getAttribute(USER_SESSION_KEY);
    }

    public static boolean isValid(HttpSession session, Question question) {
        if (!isLoginUser(session)) {
            return false;
        }
        User loginUser = getUserFromSession(session);
        return question.matchUserId(loginUser.getUserId());
    }
}
