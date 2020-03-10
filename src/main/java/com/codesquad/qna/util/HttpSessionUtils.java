package com.codesquad.qna.util;

import com.codesquad.qna.model.User;
import javax.servlet.http.HttpSession;

public class HttpSessionUtils {

    public static final String USER_SESSION_KEY = "sessionedUser";

    public static boolean isNotLoggedIn(HttpSession session) {
        Object tempUser = session.getAttribute(HttpSessionUtils.USER_SESSION_KEY);
        return tempUser == null;
    }

    public static User getUserFromSession(HttpSession session) {
        if (isNotLoggedIn(session))
            return null;
        return (User)session.getAttribute(USER_SESSION_KEY);
    }
}
