package codesquad.domain.util;

import codesquad.domain.qna.Question;
import codesquad.domain.user.User;

import javax.servlet.http.HttpSession;

public class Session {
    private static final int SESSION_TIME = 6000;
    public static final String SESSION_NAME = "sessionUser";

    public static void registerSession(HttpSession session, User user) {
        session.setAttribute(SESSION_NAME, user);
        session.setMaxInactiveInterval(SESSION_TIME);
    }

    public static void removeSession(HttpSession session) {
        session.removeAttribute(SESSION_NAME);
    }

    public static boolean isUser(HttpSession session, Long id) {
        User user = (User)session.getAttribute(SESSION_NAME);
        if(user == null || !user.identification(id)) {
            return false;
        }
        return true;
    }

    public static User obtainUser(HttpSession httpSession) {
        return (User)httpSession.getAttribute(SESSION_NAME);
    }

    public static boolean isUser(HttpSession httpSession, Question question) {
        User user = (User)httpSession.getAttribute(SESSION_NAME);
        if(user == null || !question.identification(user)) {
            return false;
        }
        return true;
    }

    public static boolean isSession(HttpSession session) {
        return (User)session.getAttribute(SESSION_NAME) != null;
    }
}
