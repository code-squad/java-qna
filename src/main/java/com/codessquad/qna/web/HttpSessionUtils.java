package com.codessquad.qna.web;

import com.codessquad.qna.domain.User;
import javax.servlet.http.HttpSession;

public class HttpSessionUtils {

  public static final String USER_SESSION_KEY = "sessionUser";

  public static boolean isLoginUser(HttpSession httpSession) {
    Object sessionObject = httpSession.getAttribute(USER_SESSION_KEY);

    if (sessionObject == null) {
      return false;
    }
    return true;
  }

  public static User getUserFromSession(HttpSession httpSession) {
    if (!isLoginUser(httpSession)) {
      return null;
    }
    return (User) httpSession.getAttribute(USER_SESSION_KEY);
  }
}
