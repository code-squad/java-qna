package com.codessquad.qna.global.config;

import com.codessquad.qna.utils.HttpSessionUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class UserLoginCheckInterceptor implements HandlerInterceptor {

  private static final Logger log = LoggerFactory.getLogger(UserLoginCheckInterceptor.class);

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

    log.info("preHandle enter : {}, method : {}", request.getRequestURI(), request.getMethod());
    HttpSession session = request.getSession();
    if (HttpSessionUtil.notLoggedIn(session)) {
      response.sendRedirect("/users/login");
      return false;
    }

    log.info("preHandle out");
    return true;
  }
}