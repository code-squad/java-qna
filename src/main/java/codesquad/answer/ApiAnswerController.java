package codesquad.answer;

import codesquad.exception.Result;
import codesquad.question.Question;
import codesquad.question.QuestionRepository;
import codesquad.user.User;
import codesquad.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping("")
    public Answer create(@PathVariable long questionId, String contents, Model model, HttpSession session) {
        if (isValid(model, session)) return null;
        // issue : result를 사용한 채로, null이 아닌 로그인 페이지로 어떻게 이동할 것인가?

        User sessionedUser = SessionUtil.getUserFromSession(session);
        Question question = questionRepository.findById(questionId).get();
        Answer answer = Answer.newInstance(sessionedUser, question, contents);

        return answerRepository.save(answer);
    }

    private boolean isValid(Model model, HttpSession session) {
        Result result = valid(session);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return true;
        }
        return false;
    }

    private Result valid(HttpSession session) {
        if (!SessionUtil.isSessionedUser(session)) {
            return Result.fail("You need login");
        }

        return Result.success();
    }
}