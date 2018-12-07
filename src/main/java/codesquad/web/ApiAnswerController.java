package codesquad.web;

import codesquad.HttpSessionUtils;
import codesquad.domain.answer.Answer;
import codesquad.domain.answer.AnswerRepository;
import codesquad.domain.answer.Result;
import codesquad.exception.AnswerException;
import codesquad.exception.QuestionException;
import codesquad.domain.question.Question;
import codesquad.domain.question.QuestionRepository;
import codesquad.domain.user.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {
    private static final Logger logger = LogManager.getLogger(ApiAnswerController.class);

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping("")
    public Answer create(@PathVariable Long questionId, String contents, HttpSession session) {
        logger.info("api answer info");
        HttpSessionUtils.isLoginUser(session);
        User loginUser = HttpSessionUtils.getUserFormSession(session);

        Question question = questionRepository.findById(questionId).orElseThrow(QuestionException::new);
        Answer answer = new Answer(question,loginUser, contents);
        question.addAnswer(answer);


        return answerRepository.save(answer);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        logger.info("api answer deleted");
        User loginUser = HttpSessionUtils.getUserFormSession(session);
        Answer answer = answerRepository.findById(id).orElseThrow(AnswerException::new);

        if (!answer.matchUser(loginUser)) {
            return Result.error("질문 작성자와 다릅니다.");
        }

        answer.deleted();
        answerRepository.save(answer);
        return Result.ok();
    }

}
