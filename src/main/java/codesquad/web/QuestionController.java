package codesquad.web;

import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import codesquad.domain.Result;
import codesquad.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    private static final String USER_LOGIN = "/user/login";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final Logger log = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private QuestionRepository questionsRepository;

    @GetMapping
    public String form(HttpSession session) {
        log.info("질문 폼");

        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/users/loginForm";
        }

        return "/qna/form";
    }

    @PostMapping
    public String inputQuestion(String title, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/user/login";
        }

        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        Question question = new Question(sessionUser, title, contents);
        if (!question.matchUser(sessionUser)) {
            throw new IllegalStateException("InputQuestion error");
        }

        questionsRepository.save(question);

        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String questionDetail(@PathVariable Long id, Model model) {
        Question question = getQuestionFromRepo(id);
        model.addAttribute("question", question);

        return "/qna/show";
    }

    private Question getQuestionFromRepo(Long id) {
        return questionsRepository.findOne(id);
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        Result result = valid(id, session);
        if (!result.isValid()) {
            model.addAttribute(ERROR_MESSAGE, result.getErrorMessage());
            return USER_LOGIN;
        }

        // TODO 체크할때 repository에서 한번 꺼내고 모델에 추가할때 한번 더 꺼내는데 괜찮은가?
        model.addAttribute("question", getQuestionFromRepo(id));
        return "/qna/updateForm";
    }

    private Result valid(Long id, HttpSession session) {
        if (!checkLoginUser(session)) {
            return Result.fail("로그인이 필요합니다");
        }

        Question question = getQuestionFromRepo(id);
        User user = HttpSessionUtils.getUserFromSession(session);
        if (!question.matchUser(user)) {
            return Result.fail("자신이 쓴 글만 수정, 삭제가 가능합니다");
        }

        return Result.ok();
    }

    private boolean checkLoginUser(HttpSession session) {
        return HttpSessionUtils.isLoginUser(session);
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, Question updatedQuestion, Model model, HttpSession session) {
        Result result = valid(id, session);
        if (!result.isValid()) {
            model.addAttribute(ERROR_MESSAGE, result.getErrorMessage());
            return USER_LOGIN;
        }

        Question question = questionsRepository.getOne(id);
        User user = HttpSessionUtils.getUserFromSession(session);
        question.update(updatedQuestion, user);

        questionsRepository.save(question);

        // TODO String Format 과의 차이?
        return "redirect:/questions/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, Model model, HttpSession session) {
        Result result = valid(id, session);
        if (!result.isValid()) {
            model.addAttribute(ERROR_MESSAGE, result.getErrorMessage());
            return USER_LOGIN;
        }

        // TODO 질문 삭제시 답글까지 같이 삭제하는 기능?
        questionsRepository.delete(id);

        return "redirect:/";
    }
}
