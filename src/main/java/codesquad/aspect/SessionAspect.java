package codesquad.aspect;

import codesquad.config.HttpSessionUtils;
import codesquad.question.Result;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

import static org.slf4j.LoggerFactory.getLogger;

@Aspect
@Component
public class SessionAspect {
    private static final Logger log = getLogger(SessionAspect.class);

    @Pointcut("execution(* codesquad.question.QuestionController.*(..)) && args(session, model, ..)")
    public void questionSession(HttpSession session, Model model){
    }

    @Pointcut("execution(* codesquad.user.UserController.*(..)) && args(session, model, ..)")
    public void userSession(HttpSession session, Model model) {
    }

    @Around("questionSession(session, model) || userSession(session, model)")
    public Object checkSession(ProceedingJoinPoint jp, HttpSession session, Model model) throws Throwable {
        Result result = valid(session);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "user/login";
        }
        return jp.proceed();
    }

    private Result valid(HttpSession session) {
        if (!HttpSessionUtils.isLogin(session)) {
            return Result.fail("로그인이 필요합니다.");
        }
        return Result.ok();
    }
}
