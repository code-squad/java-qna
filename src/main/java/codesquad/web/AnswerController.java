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
            return "/users/loginForm";
        }

        User writer = SessionUtils.getUserFromSession(session);
        Question question = questionRepository.getOne(questionId);
        Answer newAnswer = new Answer(writer, question, comment);

        answerRepository.save(newAnswer);
        return "redirect:/questions/{questionId}";
    }

    @PutMapping("/questions/{questionId}/answers/{answerId}")
    public String update(@PathVariable Long questionId, @PathVariable Long answerId, String comment, HttpSession session) {
        User updateUser = SessionUtils.getUserFromSession(session);
        Answer oldAnswer = answerRepository.findOne(answerId);
        Question question = questionRepository.findOne(questionId);
        Answer updateAnswer = new Answer(updateUser, question, comment);

        if (!SessionUtils.isLoginUser(session)) {
            return "/users/loginForm";
        }
        if (!updateUser.isMatchedUserId(oldAnswer)) {
            throw new IllegalStateException("question.id.mismatch");
        }
        if (!oldAnswer.update(updateAnswer)) {
            throw new IllegalStateException("");
        }
        answerRepository.save(oldAnswer);

        return "redirect:/questions/{questionId}";
    }

    @DeleteMapping("/questions/{questionId}/answers/{answerId}")
    public String delete(@PathVariable Long answerId, HttpSession session) {
        User deleteUser = SessionUtils.getUserFromSession(session);
        Answer deleteAnswer = answerRepository.findOne(answerId);

        if (!SessionUtils.isLoginUser(session)) {
            return "/users/loginForm";
        }
        if (!deleteUser.isMatchedUserId(deleteAnswer)) {
            throw new IllegalStateException("question.id.mismatch");
        }

        answerRepository.delete(answerId);
        return "redirect:/questions/{questionId}";
    }

    @GetMapping("/questions/{questionId}/answers/{answerId}/form")
    public String updateForm(@PathVariable Long questionId, @PathVariable Long answerId, HttpSession session, Model model) {
        User updateUser = SessionUtils.getUserFromSession(session);
        Answer updateAnswer = answerRepository.findOne(answerId);

        if (!SessionUtils.isLoginUser(session)) {
            return "/users/loginForm";
        }
        if (!updateUser.isMatchedUserId(updateAnswer)) {
            throw new IllegalStateException("question.id.mismatch");
        }

        model.addAttribute("question", questionRepository.findOne(questionId));
        model.addAttribute("answers", answerRepository.findByQuestionId(questionId));
        model.addAttribute("answersCount", answerRepository.findByQuestionId(questionId).size());

        return "/qna/answerUpdateForm";
    }
}
