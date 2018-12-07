package codesquad.domain.util;

import codesquad.domain.qna.Question;
import codesquad.domain.user.FailureTypeException;
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

    public static void isUser(HttpSession session, Long id) throws SessionMaintenanceException {
        User user = (User)session.getAttribute(SESSION_NAME);

        isSession(session);

        isInPerson(user, id);
    }

    public static User obtainUser(HttpSession httpSession) {
        return (User)httpSession.getAttribute(SESSION_NAME);
    }

    public void isUser(HttpSession httpSession, Question question) throws SessionMaintenanceException {
        User user = (User)httpSession.getAttribute(SESSION_NAME);

        isSession(httpSession);

        isInPerson(user, question);
    }

    public static void isSession(HttpSession session) throws SessionMaintenanceException {
        if(session.getAttribute(SESSION_NAME) == null) {
            throw new SessionMaintenanceException("로그인이 필요한 서비스입니다.");
        }
    }

    public static void isInPerson(User user, Long id) {
        if(!user.identification(id)) {
            throw new FailureTypeException("본인만 이용 가능한 서비스입니다.");
        }
    }

    public static void isInPerson(User user, Question question) {
        if(!question.identification(user)) {
            throw new FailureTypeException("본인만 이용 가능한 서비스입니다.");
        }
    }

    public static boolean isInPerson(Question question, HttpSession httpSession) {
        return question.identification((User)httpSession.getAttribute(SESSION_NAME));
    }
}
