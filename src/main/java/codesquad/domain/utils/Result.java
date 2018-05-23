package codesquad.domain.utils;

import codesquad.domain.model.Answer;
import codesquad.domain.model.Question;
import codesquad.domain.model.User;
import org.springframework.context.annotation.Primary;

import javax.servlet.http.HttpSession;

import java.util.function.Predicate;

import static codesquad.domain.utils.HttpSessionUtils.isLoginUser;

public class Result {

    private boolean valid;

    private String errorMessage;

    private Result(boolean valid, String errorMessage) {
        this.valid = valid;
        this.errorMessage = errorMessage;
    }

    public static Result ok() {
        return new Result(true, null);
    }

    public static Result fail(String errorMessage) {
        return new Result(false, errorMessage);
    }

    public boolean isValid() {
        return valid;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public static Result valid(HttpSession session, Answer answer) {
        if (!isLoginUser(session)) {
            return Result.fail("로그인이 필요합니다.");
        }
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        if (!answer.matchWriter(loginUser)) {
            return Result.fail("자신이 쓴 답변만 수정, 삭제가 가능합니다.");
        }
        return Result.ok();
    }

    public static Result valid(HttpSession session, Question question) {
        if (!isLoginUser(session)) {
            return Result.fail("로그인이 필요합니다.");
        }
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        if (!question.matchWriter(loginUser)) {
            return Result.fail("자신이 쓴 글만 수정, 삭제가 가능합니다.");
        }
        return Result.ok();
    }

    public static <T1> Result vv(HttpSession session, T1 t1, Predicate<T1> function) {
        if (!isLoginUser(session)) {
            return Result.fail("로그인이 필요합니다.");
        }
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        if (!function.test(t1)) {
            return Result.fail("자신이 쓴 글만 수정, 삭제가 가능합니다.");
        }
        return Result.ok();
    }

    public static Result valid2(HttpSession session, Question question) {
        if (!isLoginUser(session)) {
            return Result.fail("로그인이 필요합니다.");
        }
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        if (!question.matchWriter(loginUser)) {
            return Result.fail("자신이 쓴 글만 수정, 삭제가 가능합니다.");
        }
        return Result.ok();
    }

    public static Result valid(HttpSession session) {
        if (!isLoginUser(session)) {
            return Result.fail("로그인이 필요합니다.");
        }
        return Result.ok();
    }
}
