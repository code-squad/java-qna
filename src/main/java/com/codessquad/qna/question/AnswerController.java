package com.codessquad.qna.question;

import com.codessquad.qna.common.CommonConstants;
import com.codessquad.qna.user.User;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Controller
public class AnswerController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping("/questions/{questionId}/answers")
    public String createAnswer(@PathVariable Long questionId, @RequestParam String comments, HttpSession session) {
        Object loginUserAttribute = session.getAttribute(CommonConstants.SESSION_LOGIN_USER);
        if (loginUserAttribute == null) {
            return CommonConstants.REDIRECT_LOGIN_PAGE;
        }

        try {
            User loginUser = (User) loginUserAttribute;
            Question question = questionRepository.findById(questionId).orElseThrow(() -> new NotFoundException("못찾음"));
            Answer answer = new Answer(loginUser, question, comments);
            answerRepository.save(answer);
        } catch (NotFoundException e) {
            return CommonConstants.ERROR_QUESTION_NOT_FOUND;
        }

        return "redirect:/questions/" + questionId;
    }

    @GetMapping("/questions/{questionId}/answers/{answerId}/form")
    public String goUpdateAnswerForm(@PathVariable Long questionId,
                                     @PathVariable Long answerId,
                                     HttpSession session,
                                     Model model) {
        Object loginUserAttribute = session.getAttribute(CommonConstants.SESSION_LOGIN_USER);
        if (loginUserAttribute == null) {
            return CommonConstants.REDIRECT_LOGIN_PAGE;
        }

        try {
            User loginUser = (User) loginUserAttribute;
            Question question = questionRepository.findById(questionId).orElseThrow(() -> new NotFoundException("못찾음"));
            Answer answer = answerRepository.findByQuestionIdAndId(questionId, answerId);
            if (!loginUser.equals(answer.getWriter())) {
                return "redirect:/questions/" + questionId;
            }
            model.addAttribute("question", question);
            model.addAttribute("answer", answer);
        } catch (NotFoundException e) {
            return CommonConstants.ERROR_QUESTION_NOT_FOUND;
        }

        return "questions/answer-modify-form";
    }

    @PutMapping("/questions/{questionId}/answers/{answerId}")
    public String updateAnswer(@PathVariable Long questionId,
                               @PathVariable Long answerId,
                               @RequestParam String comments,
                               HttpSession session) {
        Object loginUserAttribute = session.getAttribute(CommonConstants.SESSION_LOGIN_USER);
        if (loginUserAttribute == null) {
            return CommonConstants.REDIRECT_LOGIN_PAGE;
        }

        User loginUser = (User) loginUserAttribute;
        Answer answer = answerRepository.findByQuestionIdAndId(questionId, answerId);
        if (!loginUser.equals(answer.getWriter())) {
            return "redirect:/questions/" + questionId;
        }
        answer.setComment(comments);
        answer.setUpdatedDateTime(LocalDateTime.now());
        answerRepository.save(answer);

        return "redirect:/questions/" + questionId;
    }

    @DeleteMapping("/questions/{questionId}/answers/{answerId}")
    public String deleteAnswer(@PathVariable Long questionId, @PathVariable Long answerId, HttpSession session) {
        Object loginUserAttribute = session.getAttribute(CommonConstants.SESSION_LOGIN_USER);
        if (loginUserAttribute == null) {
            return CommonConstants.REDIRECT_LOGIN_PAGE;
        }

        User loginUser = (User) loginUserAttribute;
        Answer answer = answerRepository.findByQuestionIdAndId(questionId, answerId);
        if (!loginUser.equals(answer.getWriter())) {
            return "redirect:/questions/" + questionId;
        }
        answerRepository.delete(answer);
        return "redirect:/questions/" + questionId;
    }

}
