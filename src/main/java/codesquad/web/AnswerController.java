package codesquad.web;


import codesquad.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class AnswerController {
    private static final Logger log = LoggerFactory.getLogger(AnswerController.class);

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    QuestionRepository questionRepository;

    @PostMapping("/questions/{questionId}/answers")
    public String answer(@PathVariable Long questionId, String comment, HttpSession session, Model model) {
        if (!SessionUtils.isLoginUser(session)) {
            throw new IllegalStateException("need login");
//            return "/users/loginForm";
        }

        User writer = SessionUtils.getUserFromSession(session);
        Question question = questionRepository.getOne(questionId);
        question.increaseAnswersCount();
        Answer newAnswer = new Answer(writer, question, comment);

        answerRepository.save(newAnswer);
        return "redirect:/questions/{questionId}";
    }

    @PutMapping("/questions/{questionId}/answers/{answerId}")
    public String update(@PathVariable Long questionId, @PathVariable Long answerId, String comment, HttpSession session, Model model) {
        Answer oldAnswer = answerRepository.findOne(answerId);
        Result result = valid(session, oldAnswer);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getMessage());
            return "/user/login";
        }

        User updateUser = SessionUtils.getUserFromSession(session);
        Question question = questionRepository.findOne(questionId);
        Answer updateAnswer = new Answer(updateUser, question, comment);
        oldAnswer.update(updateAnswer, updateUser);
        answerRepository.save(oldAnswer);

        return "redirect:/questions/{questionId}";
    }

    @DeleteMapping("/questions/{questionId}/answers/{answerId}")
    public String delete(@PathVariable Long questionId, @PathVariable Long answerId, HttpSession session, Model model) {
        Answer deleteAnswer = answerRepository.findOne(answerId);
        Result result = valid(session, deleteAnswer);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getMessage());
            return "/user/login";
        }

        Question question = questionRepository.findOne(questionId);
        question.decreaseAnswersCount();

        deleteAnswer.delete();
        answerRepository.save(deleteAnswer);
        return "redirect:/questions/{questionId}";
    }

    @GetMapping("/questions/{questionId}/answers/{answerId}/form")
    public String updateForm(@PathVariable Long questionId, @PathVariable Long answerId, HttpSession session, Model model) {
        Answer updateAnswer = answerRepository.findOne(answerId);
        Result result = valid(session, updateAnswer);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getMessage());
            return "/user/login";
        }

        Question question = questionRepository.findOne(questionId);
        question.setAnswers(answerRepository.findByQuestionId(questionId));

        model.addAttribute("question", question);
        model.addAttribute("editingAnswer", answerRepository.findOne(answerId));

        return "/qna/answerUpdateForm";
    }

    public Result valid(HttpSession session, Answer answer) {
        if (!SessionUtils.isLoginUser(session)) {
            return Result.NEED_LOGIN;
        }

        User sessionUser = SessionUtils.getUserFromSession(session);
        log.debug("session user is : {}", sessionUser);
        if (!answer.isMatchedUserId(sessionUser)) {
            return Result.MISMATCH_USER;
        }

        return Result.SUCCESS;
    }
}
