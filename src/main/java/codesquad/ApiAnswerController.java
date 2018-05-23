package codesquad;

import codesquad.exceptions.NoSessionedUserException;
import codesquad.exceptions.UnauthorizedRequestException;
import codesquad.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static codesquad.HttpSessionUtils.*;

@RestController
@RequestMapping("/api/questions/{questionId}")
public class ApiAnswerController {
    private static final Logger logger = LoggerFactory.getLogger(ApiAnswerController.class);

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    QuestionRepository questionRepository;

    @PutMapping("submitAnswer")
    public Answer submitAnswer(HttpSession session, Answer answer, @PathVariable Long questionId) {
        try {
            User user = getUserFromSession(session);
            answer.setUser(user);
            Question question = questionRepository.findQuestionByQuestionId(questionId);
            answer.setQuestion(question);
            return answerRepository.save(answer);
        } catch (NoSessionedUserException e) {
            logger.debug(e.getMessage());
            return null;
            //return "redirect:/users/loginForm";
        }
    }

    @DeleteMapping("answers/{id}/delete")
    public String deleteAnswer(HttpSession session, @PathVariable Long questionId, @PathVariable Long id) {
        try {
            User user = getUserFromSession(session);
            Answer answer = answerRepository.findOne(id);
            answer.flagDeleted(answerRepository, user);
            logger.debug("Answer deleted!");
            return "redirect:/questions/{questionId}";
        } catch (NoSessionedUserException e) {
            logger.debug(e.getMessage());
            return "redirect:/users/loginForm";
        } catch (UnauthorizedRequestException e) {
            logger.debug(e.getMessage());
            return "redirect:/questions/{questionId}";
        }
    }
}