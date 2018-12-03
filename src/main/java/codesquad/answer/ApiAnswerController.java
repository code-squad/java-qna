package codesquad.answer;

import codesquad.exception.Result;
import codesquad.question.Question;
import codesquad.question.QuestionRepository;
import codesquad.user.User;
import codesquad.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping("")
    public Answer create(@PathVariable long questionId, String contents, HttpSession session) {
        if (!SessionUtil.isSessionedUser(session)) {
            // 현 상황에서 Result 활용할 수 있는 방법은?
            return null;
        }

        User sessionedUser = SessionUtil.getUserFromSession(session);
        Question question = questionRepository.findById(questionId).get();
        Answer answer = Answer.newInstance(sessionedUser, question, contents);
        question.addAnswer();

        return answerRepository.save(answer);
    }

    @DeleteMapping("{id}")
    public Result delete(@PathVariable long questionId, @PathVariable long id, HttpSession session) {
        if (!SessionUtil.isSessionedUser(session)) {
            return Result.fail("You need login");
        }

        Answer answer = answerRepository.findById(id).orElse(null);
        User sessionedUser = SessionUtil.getUserFromSession(session);

        if (!answer.delete(sessionedUser)) {
            return Result.fail("You can't edit the other user's answer");
        }
        answerRepository.save(answer);

        Question question = questionRepository.findById(questionId).get();
        question.deletedAnswer();
        questionRepository.save(question);

        return Result.success();
    }
}