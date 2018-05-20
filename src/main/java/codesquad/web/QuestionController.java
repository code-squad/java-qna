package codesquad.web;

import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
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
    private static final String REDIRECT_USERS_LOGIN_FORM = "redirect:/users/loginForm";
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
    public String inputQuestion(Question question, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/user/login";
        }

        User user = (User) session.getAttribute(HttpSessionUtils.USER_SESSION_KEY);
        question.setWriter(user.getName());
        question.setWriterId(user.getUserId());

        questionsRepository.save(question);

        return "redirect:/";
    }

    @GetMapping("/{index}")
    public String questionDetail(@PathVariable int index, Model model) {
        Question question = getQuestionFromRepo(index);
        model.addAttribute("question", question);

        return "/qna/show";
    }

    private Question getQuestionFromRepo(int index) {
        return questionsRepository.findOne(index);
    }

    @GetMapping("/{index}/form")
    public String updateForm(@PathVariable int index, Model model, HttpSession session) {
        if (!checkSessionByIndex(index, session)) {
            return REDIRECT_USERS_LOGIN_FORM;
        }

        // TODO 체크할때 repository에서 한번 꺼내고 모델에 추가할때 한번 더 꺼내는데 괜찮은가?
        model.addAttribute("question", getQuestionFromRepo(index));
        return "/qna/updateForm";
    }

    private boolean checkSessionByIndex(int index, HttpSession session) {
        if (!checkLoginUser(session)) {
            return false;
        }

        Question question = getQuestionFromRepo(index);
        checkSessionEqualsQuestion(session, question);

        return true;
    }

    private boolean checkLoginUser(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return false;
        }

        return true;
    }

    private void checkSessionEqualsQuestion(HttpSession session, Question question) {
        User userFromSession = HttpSessionUtils.getUserFromSession(session);
        if (!question.matchUserId(userFromSession)) {
            throw new IllegalStateException("Don't manipulate Other's contents");
        }
    }

    @PutMapping("/{index}")
    public String update(@PathVariable int index, Question updatedQuestion, HttpSession session) {
        if (!checkLoginUser(session)) {
            throw new IllegalStateException("Do not modify other user");
        }

        Question question = questionsRepository.getOne(index);
        checkSessionEqualsQuestion(session, question);

        question.update(updatedQuestion);
        questionsRepository.save(question);

        return "redirect:/questions/{index}";
    }

    @DeleteMapping("/{index}")
    public String delete(@PathVariable int index, HttpSession session) {
        if (!checkSessionByIndex(index, session)) {
            return REDIRECT_USERS_LOGIN_FORM;
        }

        Question question = questionsRepository.getOne(index);
        checkSessionEqualsQuestion(session, question);

        questionsRepository.delete(index);

        return "redirect:/";
    }
}
