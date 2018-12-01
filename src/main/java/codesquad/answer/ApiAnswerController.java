package codesquad.answer;

import codesquad.HttpSessionUtils;
import codesquad.exception.AnswerException;
import codesquad.exception.QuestionException;
import codesquad.exception.UserException;
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

        HttpSessionUtils.isLoginUser(session);

        User loginUser = HttpSessionUtils.getUserFormSession(session);
        Question question = questionRepository.findById(questionId).orElse(null);
        Answer answer = new Answer(question,loginUser, contents);
        return answerRepository.save(answer);


        /*//logger.info("answer info");
        HttpSessionUtils.isLoginUser(session);
        Question question = questionRepository.findById(questionId).orElseThrow(QuestionException::new);
        question.addAnswer(new Answer(question, HttpSessionUtils.getUserFormSession(session), contents));

        return questionRepository.save(question);*/
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        Answer answer = getMatchingAnswer(id, session);
        answer.deleted();
        answerRepository.save(answer);
        return Result.ok();

    }



    private Answer getMatchingAnswer( @PathVariable long id, HttpSession session) {
        User loginUser = HttpSessionUtils.getUserFormSession(session);
        Answer answer = answerRepository.findById(id).orElseThrow(AnswerException::new);
        notMatchUser(answer.matchUser(loginUser));
        return answer;
    }

    private void notMatchUser(boolean isMatchUser) {
        if (!isMatchUser) {
            throw new UserException("작성자와 아이디가 다릅니다. 다시 로그인 해주세요");
        }
    }
}
