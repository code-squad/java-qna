package codesquad;

import codesquad.exceptions.NoSessionedUserException;
import codesquad.exceptions.UnauthorizedRequestException;
import codesquad.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static codesquad.HttpSessionUtils.*;

@RestController
@RequestMapping("/api/questions/{questionId}")
public class ApiAnswerController {
    private static final Logger logger = LoggerFactory.getLogger(ApiAnswerController.class);

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @PutMapping("submitAnswer")
    public Answer submitAnswer(HttpSession session, Answer answer, @PathVariable Long questionId) {
        try {
            User user = getUserFromSession(session);
            answer.setUser(user);
            Question question = questionRepository.findOne(questionId);
            answer.setQuestion(question);
            logger.debug("Adding Answer...");
            return answerRepository.save(answer);
        } catch (NoSessionedUserException e) {
            logger.debug(e.getMessage());
            return null;
        }
    }

    @DeleteMapping("answers/{id}/delete")
    public Result deleteAnswer(HttpSession session, @PathVariable Long id) {
        try {
            User user = getUserFromSession(session);
            Answer answer = answerRepository.findOne(id);
            Result result = answer.flagDeleted(user);
            answerRepository.save(answer);
            logger.debug("Answer Deleted!");
            return result;
        } catch (NoSessionedUserException | UnauthorizedRequestException e) {
            logger.debug(e.getMessage());
            return Result.ofFailure();
        }
    }
}