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


    //TODO : implement checkWriter AOP
//    @Pointcut("@annotation(codesquad.aspect.WriterCheck) &&" +
//            "execution(* codesquad..*.*(javax.servlet.http.HttpSession,Long,..)) &&" +
//            "args(session,id,..)")
//    public void checkWriter(HttpSession session, long id) {
//    }

    //    @Around("@annotation(codesquad.aspect.LoginCheck) && " +
//            "execution(* codesquad..*.*(javax.servlet.http.HttpSession,..)) && " +
//            "args(session,..)")
    @Around("checkLogin(session)")
    public String checkLoginLogic(ProceedingJoinPoint pjp, HttpSession session) throws Throwable {
        if (session.getAttribute(User.SESSION_NAME) == null) {
            return "redirect:/users/login?debug";
        }
        return (String) pjp.proceed();
    }

//    @Around("checkWriter(session,id)")
//    public String checkWriter(ProceedingJoinPoint pjp, HttpSession session, long id) throws Throwable {
//        System.out.println("DEBUGNNN : WRITE CHECK"+session + id);
//        return (String) pjp.proceed();
//    }

}
