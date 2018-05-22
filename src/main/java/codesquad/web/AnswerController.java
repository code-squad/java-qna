package codesquad.web;

import codesquad.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

// answer는 question에 종속적이다
@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
    private static final String REDIRECT_USERS_LOGIN_FORM = "redirect:/users/loginForm";
    private static final String REDIRECT_QUESTIONS_QUESTION_ID = "redirect:/questions/{questionId}";

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping
    public String create(@PathVariable Long questionId, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return REDIRECT_USERS_LOGIN_FORM;
        }

        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findOne(questionId);
        Answer answer = new Answer(loginUser, question, contents);
        answerRepository.save(answer);

        return REDIRECT_QUESTIONS_QUESTION_ID;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return REDIRECT_USERS_LOGIN_FORM;
        }

        // TODO 삭제하려는 답변의 작성자와 현재 세션의 작성자가 같은지 확인

        Answer answer = answerRepository.getOne(id);
        if (!answer.checkEqualSession(session)) {
            throw new IllegalStateException("delete error");
        }

        answerRepository.delete(id);

        return REDIRECT_QUESTIONS_QUESTION_ID;
    }

    @PutMapping("/{id}")
    public String modify(@PathVariable Long id, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return REDIRECT_USERS_LOGIN_FORM;
        }

        Answer answer = answerRepository.getOne(id);
        if (!answer.checkEqualSession(session)) {
            throw new IllegalStateException("modify error");
        }

        // TODO 답변 수정 구현

        return REDIRECT_QUESTIONS_QUESTION_ID;
    }
}
