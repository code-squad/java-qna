package codesquad.web;

import codesquad.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    private static final Logger log = LoggerFactory.getLogger(HttpSessionUtils.class);
    
    static final String USER_SESSION_KEY = "sessionedUser";

    static boolean isLoginUser(HttpSession session) {
        return session.getAttribute(USER_SESSION_KEY) != null;
    }

    public static User getUserFromSession(HttpSession session) {
        if (!isLoginUser(session)) {
            log.info("세션이 널이다");
            
            return null;
        }

        return (User) session.getAttribute(USER_SESSION_KEY);
    }
}
