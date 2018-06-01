package codesquad.config;

import codesquad.domain.Result;
import codesquad.web.SessionUtils;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class HttpInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws IOException, ServletException {

        HttpSession session = request.getSession();
        if (!SessionUtils.isLoginUser(session)) {
            request.setAttribute("errorMessage", Result.NEED_LOGIN.getMessage());
//            response.sendRedirect("/users/loginForm");
            RequestDispatcher rd = request.getRequestDispatcher("/users/loginForm");
            rd.forward(request, response);
            return false;
        }
        return true;
    }
}
