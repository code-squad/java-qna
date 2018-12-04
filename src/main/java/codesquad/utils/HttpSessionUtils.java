package codesquad.utils;

import codesquad.answer.Answer;
import codesquad.question.Question;
import codesquad.user.User;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "loginUser";

    public static boolean isLoginUser(HttpSession session) {
        User loginUser = getUserFromSession(session);
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
        return question.matchUser(loginUser);
    }

    public static boolean isValid(User loginUser, Question question) {
        return question.matchUser(loginUser);
    }

    public static boolean isValid(HttpSession session, Answer answer) {
        if (!isLoginUser(session)) {
            return false;
        }
        User loginUser = getUserFromSession(session);
        return answer.matchUser(loginUser);
    }
}
