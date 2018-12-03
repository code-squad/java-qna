package codesquad.aspect;

import codesquad.user.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@org.aspectj.lang.annotation.Aspect
@Component
public class Aspect {
    private static final Logger logger = LoggerFactory.getLogger(Aspect.class);

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

    @After("execution(* codesquad..*.*(..))")
    public void debugLog(JoinPoint joinPoint) {
        StringBuilder sb = new StringBuilder("signature : ");
        sb.append(joinPoint.getSignature().toString());
        sb.append(System.lineSeparator());

        for (Object object : joinPoint.getArgs()) {
            sb.append(object.toString());
            sb.append(System.lineSeparator());
        }

        logger.info("{}", sb.toString());
    }
}
