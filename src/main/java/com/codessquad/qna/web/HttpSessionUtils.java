package com.codessquad.qna.web;

import com.codessquad.qna.domain.Users;
import javax.servlet.http.HttpSession;

public class HttpSessionUtils {

  public static final String USER_SESSION_KEY = "sessionUser";

  public static boolean isLoggedIn(HttpSession httpSession) {
    Object sessionObject = httpSession.getAttribute(USER_SESSION_KEY);
    return sessionObject == null;
  }

  public static Users getUserFromSession(HttpSession httpSession) {
    if (isLoggedIn(httpSession)) {
      return null;
    }
    return (Users) httpSession.getAttribute(USER_SESSION_KEY);
  }
}