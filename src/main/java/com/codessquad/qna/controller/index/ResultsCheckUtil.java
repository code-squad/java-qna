package com.codessquad.qna.controller.index;

import com.codessquad.qna.domain.Users;
import com.codessquad.qna.utils.ErrorMessageUtil;
import com.codessquad.qna.utils.HttpSessionUtil;
import java.util.Objects;
import javax.servlet.http.HttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class ResultsCheckUtil {

  public static Results loginCheckResult() {
    HttpSession httpSession = getHttpSession();
    if (HttpSessionUtil.notLoggedIn(httpSession)) {
      return Results.fail(ErrorMessageUtil.LOGIN);
    }
    return Results.ok();
  }

  public static Results userValidCheckResult(Long Id) {
    HttpSession httpSession = getHttpSession();
    Users sessionUser = (Users) httpSession.getAttribute("sessionUser");
    if (!sessionUser.matchId(Id)) {
      return Results.fail(ErrorMessageUtil.UNAUTHORIZED);
    }
    return Results.ok();
  }

  static HttpSession getHttpSession() {
      ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder
          .getRequestAttributes();
      return Objects.requireNonNull(attr).getRequest().getSession(true);
  }
}