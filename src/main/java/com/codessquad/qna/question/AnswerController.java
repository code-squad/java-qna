package com.codessquad.qna.question;

import com.codessquad.qna.common.CommonString;
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
    public String createAnswer(@PathVariable Long questionId,
                               @RequestParam String comments,
                               HttpSession session) {
        Object loginUserAttribute = session.getAttribute(CommonString.SESSION_LOGIN_USER);
        if (loginUserAttribute == null) {
            return "redirect:/users/login";
        }

        try {
            User loginUser = (User) loginUserAttribute;
            Question question = questionRepository.findById(questionId).orElseThrow(() -> new NotFoundException("못찾음"));
            Answer answer = new Answer(loginUser, question, comments);
            answerRepository.save(answer);
        } catch (NotFoundException e) {
            return "error/question_not_found";
        }

        return "redirect:/questions/" + questionId;
    }

    @GetMapping("/questions/{questionId}/answers/{answerId}/form")
    public String goUpdateAnswerForm(@PathVariable Long questionId,
                                     @PathVariable Long answerId,
                                     HttpSession session,
                                     Model model) {
        Object loginUserAttribute = session.getAttribute(CommonString.SESSION_LOGIN_USER);
        if (loginUserAttribute == null) {
            return "redirect:/users/login";
        }

        try {
            Question question = questionRepository.findById(questionId).orElseThrow(() -> new NotFoundException("못찾음"));
            Answer answer = answerRepository.findByQuestionIdAndId(questionId, answerId);
            model.addAttribute("question", question);
            model.addAttribute("answer", answer);
        } catch (NotFoundException e) {
            return "error/question_not_found";
        }

        return "questions/answer_modify_form";
    }

    @PutMapping("/questions/{questionId}/answers/{answerId}")
    public String updateAnswer(@PathVariable Long questionId,
                               @PathVariable Long answerId,
                               @RequestParam String comments,
                               HttpSession session) {
        Object loginUserAttribute = session.getAttribute(CommonString.SESSION_LOGIN_USER);
        if (loginUserAttribute == null) {
            return "redirect:/users/login";
        }

        Answer answer = answerRepository.findByQuestionIdAndId(questionId, answerId);
        answer.setComment(comments);
        answer.setUpdatedDateTime(LocalDateTime.now());
        answerRepository.save(answer);

        return "redirect:/questions/" + questionId;
    }

    @DeleteMapping("/questions/{questionId}/answers/{answerId}")
    public String deleteAnswer(@PathVariable Long questionId,
                               @PathVariable Long answerId) {
        Answer answer = answerRepository.findByQuestionIdAndId(questionId, answerId);
        answerRepository.delete(answer);
        return "redirect:/questions/" + questionId;
    }

}
