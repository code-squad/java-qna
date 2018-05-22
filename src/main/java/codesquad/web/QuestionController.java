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
        if (!question.checkEqualSession(session)) {
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
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "/user/login";
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
        if (!question.checkEqualSession(session)) {
            return Result.fail("자신이 쓴 글만 수정, 삭제가 가능합니다");
        }

        return Result.ok();
    }

    private boolean checkLoginUser(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return false;
        }

        return true;
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, Question updatedQuestion, Model model, HttpSession session) {
        Result result = valid(id, session);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "/user/login";
        }

        Question question = questionsRepository.getOne(id);
        question.update(updatedQuestion, session);

        questionsRepository.save(question);

        // TODO String Format 과의 차이?
        return "redirect:/questions/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, Model model, HttpSession session) {
        Result result = valid(id, session);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "/user/login";
        }

        questionsRepository.delete(id);

        return "redirect:/";
    }
}
