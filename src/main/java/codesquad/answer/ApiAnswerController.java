package codesquad.answer;

import codesquad.HttpSessionUtils;
import codesquad.Result;
import codesquad.question.Question;
import codesquad.question.QuestionRepository;
import codesquad.user.User;
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
    public Answer create(@PathVariable Long questionId, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoggedInUser(session)) {
            return null;
        }

        User loggedInUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(questionId).orElse(null);
        Answer answer = new Answer(question, loggedInUser, contents);
        question.addAnswer();
        questionRepository.save(question);
        return answerRepository.save(answer);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        if (!HttpSessionUtils.isLoggedInUser(session)) {
            return Result.fail("로그인이 필요합니다.");
        }

        Answer answer = answerRepository.findById(id).orElse(null);
        User loggedInUser = HttpSessionUtils.getUserFromSession(session);
        if(!answer.isMatchWriter(loggedInUser)) {
            return Result.fail("작성자만 삭제가능합니다.");
        }

        Question question = questionRepository.findById(questionId).orElse(null);
        question.deleteAnswer();
        questionRepository.save(question);
        answerRepository.deleteById(id);
        return Result.ok();
    }
}