package com.codessquad.qna.util;

import com.codessquad.qna.exception.UnauthorizedException;
import com.codessquad.qna.repository.User;
import javax.servlet.http.HttpSession;

public class HttpSessionUtil {
    public static final String USER_SESSION_KEY = "authorizedUser";

    public static boolean isAuthorizedUser(HttpSession session) {
        Object user = session.getAttribute(USER_SESSION_KEY);
        return user != null;
    }

    public static User getUserFromSession(HttpSession session) {
        if (!isAuthorizedUser(session)) {
            throw new UnauthorizedException(Paths.UNAUTHORIZED, ErrorMessages.UNAUTHORIZED);
        }
        return (User)session.getAttribute(USER_SESSION_KEY);
    }
}
