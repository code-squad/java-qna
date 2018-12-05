package codesquad.answer;

import codesquad.HttpSessionUtils;
import codesquad.Result;
import codesquad.question.QnaRepository;
import codesquad.question.Question;
import codesquad.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {

    @Autowired
    private QnaRepository qnaRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping("")
    public Answer create(@PathVariable long questionId , HttpSession session, String contents) {
        User user = HttpSessionUtils.getUserFromSession(session);
        Question question = qnaRepository.findById(questionId).orElseThrow(() -> new IllegalArgumentException("no!"));
        question.increaseAnswer();
        qnaRepository.save(question);
        return answerRepository.save(new Answer(user, question, contents));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable long questionId, @PathVariable long id, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return Result.fail("please access your id");
        }
        Answer answer = answerRepository.findById(id).orElse(null);
        User user = HttpSessionUtils.getUserFromSession(session);
        if (!answer.isSameWriter(user)) {
            return Result.fail("you can't delete other's information");
        }
        Question question = qnaRepository.findById(questionId).orElse(null);
        question.decreaseAnswer();
        qnaRepository.save(question);
        answerRepository.deleteById(id);
        return Result.ok(question);
    }

}
