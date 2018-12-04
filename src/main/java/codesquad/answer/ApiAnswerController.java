package codesquad.answer;

import codesquad.HttpSessionUtil;
import codesquad.Result;
import codesquad.qna.Question;
import codesquad.qna.QuestionRepository;
import codesquad.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController //json
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping("")
    public Answer create(@PathVariable Long questionId, String contents, HttpSession session) {
        if (!HttpSessionUtil.isLoginUser(session)) {
            return null;
        }

        User loginUser = HttpSessionUtil.getUserFromSession(session);
        Question question = questionRepository.findById(questionId).orElse(null);
        Answer answer = new Answer(loginUser, question, contents);
        question.addAnswer();
        return answerRepository.save(answer);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session, Model model) {
        Answer answer = answerRepository.findById(id).orElseThrow(NullPointerException::new);
        Result result = valid(session, answer);
        if (!result.isValid()) {
            return result;

        }
        answer.delete();
        Question question = questionRepository.findById(questionId).orElseThrow(NullPointerException::new);
        question.deleteAnswer();
        questionRepository.save(question);
        answerRepository.save(answer);
        return Result.ok();
    }

    private Result valid(HttpSession session, Answer answer) {
        if (!HttpSessionUtil.isLoginUser(session)) {
            return Result.fail("로그인이 필요합니다.");
        }
        User loginUser = HttpSessionUtil.getUserFromSession(session);
        if (!answer.isSameWriter(loginUser)) {
            return Result.fail("자신이 쓴 댓글만 수정, 삭제가 가능합니다.");
        }
        return Result.ok();
    }
}
