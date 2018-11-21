package codesquad.utils;

import codesquad.user.User;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "loginUser";
    public static final String QUESTION_UPDATE_VALUE = "form";
    public static final String QUESTION_DELETE_VALUE = "update";

    public static boolean isLoginUser(HttpSession session) {
        Object loginUser = session.getAttribute(USER_SESSION_KEY);
        return !(loginUser == null);
    }

    public static User getUserFromSession(HttpSession session) {
        return (User) session.getAttribute(USER_SESSION_KEY);
    }

//    public static boolean isValid(String writer, HttpSession session, String kind) {
//        if (QUESTION_UPDATE_VALUE.equals(kind)) {
//            if (!isLoginUser(session)) throw new IllegalStateException("You can't update other user's question! Please Login!");
//            User loginUser = HttpSessionUtils.getUserFromSession(session);
//            return loginUser.matchUserId(writer);
//        }
//        if (QUESTION_DELETE_VALUE.equals(kind)) {
//            if (!isLoginUser(session)) throw new IllegalStateException("You can't delete other user's question! Please Login!");
//            User loginUser = HttpSessionUtils.getUserFromSession(session);
//            return loginUser.matchUserId(writer);
//        }
//        return false;
//    }

//    public static boolean isPossibleQuestionDelete(long pId, HttpSession session) {
//        if (!isLoginUser(session)) throw new IllegalStateException("You can't update other user's question!");
//        User loginUser = HttpSessionUtils.getUserFromSession(session);
//        return loginUser.matchPId(pId);
//    }
}
