package codesquad.answer;

import codesquad.exception.AnswerIdNotMatchException;
import codesquad.exception.QuestionIdNotMatchException;
import codesquad.qna.Question;
import codesquad.qna.QuestionRepository;
import codesquad.util.Result;
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
    public Answer create(@PathVariable("questionId") Long questionId, String contents, HttpSession session) {
        if (!SessionUtil.isLoginUser(session)) return null;

        Question question = questionRepository.findById(questionId).orElse(null);
        Answer answer = new Answer(question, SessionUtil.getUserFromSesssion(session), contents);

        return answerRepository.save(answer);
    }

    @DeleteMapping("/{answerId}")
    public Result delete(@PathVariable("questionId") Long questionId, @PathVariable("answerId") Long id, HttpSession session) {
        if(!questionRepository.existsById(questionId)) throw new QuestionIdNotMatchException("QUESTION_ID IS NOT CORRECT");

        Answer answer = answerRepository.findById(id).orElseThrow(AnswerIdNotMatchException::new);
        if(!SessionUtil.permissionCheck(session, answer.getUser())) return Result.error("삭제할 수 없습니다.");

        answer.deletedState(SessionUtil.getUserFromSesssion(session));
        answerRepository.save(answer);

        return Result.ok();
    }

}
