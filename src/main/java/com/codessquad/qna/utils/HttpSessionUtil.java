package com.codessquad.qna.utils;

import com.codessquad.qna.domain.Users;
import javax.servlet.http.HttpSession;

public class HttpSessionUtil {

  public static final String USER_SESSION_KEY = "sessionUser";

  public static boolean notLoggedIn(HttpSession httpSession) {
    Object sessionObject = httpSession.getAttribute(USER_SESSION_KEY);
    return sessionObject == null;
  }

  public static Users getUserFromSession(HttpSession httpSession) {
    if (notLoggedIn(httpSession)) {
      return null;
    }
    return (Users) httpSession.getAttribute(USER_SESSION_KEY);
  }
}