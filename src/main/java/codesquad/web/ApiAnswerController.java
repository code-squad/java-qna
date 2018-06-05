package codesquad.web;


import codesquad.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {
    private static final Logger log = LoggerFactory.getLogger(ApiAnswerController.class);

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    QuestionRepository questionRepository;

    @PostMapping("")
    public Answer answer(@PathVariable Long questionId, String comment, HttpSession session, Model model) {
        Result result = valid(session);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getMessage());
            return null;
        }

        User writer = SessionUtils.getUserFromSession(session);
        Question question = questionRepository.getOne(questionId);
        question.increaseAnswersCount();
        Answer newAnswer = new Answer(writer, question, comment);
        return answerRepository.save(newAnswer);
    }

    @DeleteMapping("/{answerId}")
    public Result delete(@PathVariable Long questionId, @PathVariable Long answerId, HttpSession session, Model model) {
        Answer deleteAnswer = answerRepository.findOne(answerId);
        Result result = valid(session, deleteAnswer);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getMessage());
            return result;
        }

        Question question = questionRepository.findOne(questionId);
        question.decreaseAnswersCount();

        deleteAnswer.delete();
        answerRepository.save(deleteAnswer);
        return Result.SUCCESS;
    }

    private Result valid(HttpSession session) {
        if (!SessionUtils.isLoginUser(session)) {
            return Result.NEED_LOGIN;
        }
        return Result.SUCCESS;
    }

    private Result valid(HttpSession session, Answer answer) {
        Result result = valid(session);
        if (!result.isValid()) {
            return result;
        }

        User sessionUser = SessionUtils.getUserFromSession(session);
        log.debug("session user is : {}", sessionUser);
        if (!answer.isMatchedUser(sessionUser)) {
            return Result.MISMATCH_USER;
        }

        return result;
    }
}
