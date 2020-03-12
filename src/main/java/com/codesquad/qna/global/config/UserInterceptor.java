package com.codesquad.qna.global.config;

import com.codesquad.qna.util.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class UserInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(UserInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("preHandle enter : {}, method : {}", request.getRequestURI(), request.getMethod());
        if (matchQuestionURI(request.getRequestURI(), request.getMethod()))
            return true;

        HttpSession session = request.getSession();
        if (HttpSessionUtils.isNotLoggedIn(session)) {
            response.sendRedirect("/user/login");
            return false;
        }

        request.setAttribute("sessionedUser", HttpSessionUtils.getUserFromSession(session));
        log.debug("preHandle out");
        return true;
    }

    private boolean matchQuestionURI(String requestURI, String requestMethod) {
        return requestURI.matches("/questions/[0-9^/]+$") && HttpMethod.GET.matches(requestMethod);
    }
}
