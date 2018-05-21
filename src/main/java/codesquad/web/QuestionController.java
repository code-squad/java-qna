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
    public String inputQuestion(String title, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/user/login";
        }

        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        Question question = new Question(sessionUser, title, contents);
        question.checkEqualSession(session);

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
        if (!checkSessionById(id, session)) {
            return REDIRECT_USERS_LOGIN_FORM;
        }

        // TODO 체크할때 repository에서 한번 꺼내고 모델에 추가할때 한번 더 꺼내는데 괜찮은가?
        model.addAttribute("question", getQuestionFromRepo(id));
        return "/qna/updateForm";
    }

    private boolean checkSessionById(Long id, HttpSession session) {
        if (!checkLoginUser(session)) {
            return false;
        }

        Question question = getQuestionFromRepo(id);
        question.checkEqualSession(session);

        return true;
    }

    private boolean checkLoginUser(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return false;
        }

        return true;
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, Question updatedQuestion, HttpSession session) {
        if (!checkLoginUser(session)) {
            throw new IllegalStateException("Do not modify other user");
        }

        Question question = questionsRepository.getOne(id);
        question.update(updatedQuestion, session);

        questionsRepository.save(question);

        // TODO String Format 과의 차이?
        return "redirect:/questions/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        if (!checkSessionById(id, session)) {
            return REDIRECT_USERS_LOGIN_FORM;
        }

        Question question = questionsRepository.getOne(id);
        question.checkEqualSession(session);

        questionsRepository.delete(id);

        return "redirect:/";
    }
}
