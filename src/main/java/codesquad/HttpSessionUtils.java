package codesquad;

import codesquad.exception.UserException;
import codesquad.user.User;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "loginUser";

    public static void isLoginUser(HttpSession session) {
        Object sessionUser = session.getAttribute(USER_SESSION_KEY);
        if(sessionUser == null) {
            throw new UserException("로그인 하지 않았습니다. 로그인 해주세요111");
        }
    }

    public static User getUserFormSession(HttpSession session){
        isLoginUser(session);
        return (User)session.getAttribute(USER_SESSION_KEY);
    }
}
