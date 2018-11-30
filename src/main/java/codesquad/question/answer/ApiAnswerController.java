package codesquad.question.answer;

import codesquad.config.HttpSessionUtils;
import codesquad.question.QuestionNotFoundException;
import codesquad.question.QuestionRepository;
import codesquad.question.Result;
import codesquad.user.User;
import codesquad.user.UserNotFoundException;
import codesquad.user.UserRepository;
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
    private UserRepository userRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping("")
    public Object post(@PathVariable long questionId, HttpSession session, String comment, Model model) {
        Result result = valid(session);
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if(!result.isValid()) {
//            throw new IllegalArgumentException(result.getErrorMessage());
            return result.getErrorMessage();
        }
        Answer answer = new Answer(
                questionRepository.findById(questionId).orElseThrow(() -> new QuestionNotFoundException("해당 질문을 찾을 수 없습니다.")),
                userRepository.findById(sessionedUser.getId()).orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다.")),
                comment,
                false
        );
        return answerRepository.save(answer);
    }

    private Result valid(HttpSession session) {
        if (!HttpSessionUtils.isLogin(session)) {
            return Result.fail("로그인이 필요합니다.");
        }
        return Result.ok();
    }
}
