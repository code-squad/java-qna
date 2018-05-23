package codesquad;

import codesquad.exceptions.NoSessionedUserException;
import codesquad.exceptions.UnauthorizedRequestException;
import codesquad.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

import static codesquad.HttpSessionUtils.*;

@Controller
@RequestMapping("/questions/{questionId}")
public class AnswerController {
    private static final Logger logger = LoggerFactory.getLogger(AnswerController.class);

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    QuestionRepository questionRepository;

    @PutMapping("submitAnswer")
    public String submitAnswer(HttpSession session, Answer answer, @PathVariable Long questionId) {
        try {
            User user = getUserFromSession(session);
            answer.setUser(user);
            Question question = questionRepository.findQuestionByQuestionId(questionId);
            answer.setQuestion(question);
            answerRepository.save(answer);
            logger.debug("Answer posted: {}", answer);
            return "redirect:/questions/{questionId}";
        } catch (NoSessionedUserException e) {
            logger.debug(e.getMessage());
            return "redirect:/users/loginForm";
        }
    }

    @DeleteMapping("answers/{id}/delete")
    public String deleteAnswer(HttpSession session, @PathVariable Long id) {
        try {
            User user = getUserFromSession(session);
            Answer answer = answerRepository.findOne(id);
            answer.validateUser(user);
            answerRepository.delete(answer);
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