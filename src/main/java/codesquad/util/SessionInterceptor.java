package codesquad.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
public class SessionInterceptor extends HandlerInterceptorAdapter {
    private static final Logger log = LoggerFactory.getLogger(SessionInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if (!HttpSessionUtils.isLogin(session)) {
            log.info("require login");
            response.sendRedirect("/users/loginForm");
            return false;
        }
        return true;
    }
}
