package codesquad.aspect;

import codesquad.user.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@org.aspectj.lang.annotation.Aspect
@Component
public class Aspect {
    @Pointcut("@annotation(codesquad.aspect.LoginCheck) && " +
            "execution(* codesquad..*.*(javax.servlet.http.HttpSession,..)) && " +
            "args(session,..)")
    public void checkLogin(HttpSession session) {
    }


    @Around("checkLogin(session)")
    public String checkLoginLogic(ProceedingJoinPoint pjp, HttpSession session) throws Throwable {
        if (session.getAttribute(User.SESSION_NAME) == null) {
            return "redirect:/users/login?debug";
        }
        return (String) pjp.proceed();
    }
}
