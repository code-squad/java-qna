package com.codessquad.qna.web.question;

import com.codessquad.qna.common.error.exception.LoginRequiredException;
import com.codessquad.qna.common.error.exception.QuestionNotFoundException;
import com.codessquad.qna.common.utils.HttpSessionUtils;
import com.codessquad.qna.web.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @GetMapping("/{answerId}/form")
    public String goUpdateAnswerForm(@PathVariable Long questionId,
                                     @PathVariable Long answerId,
                                     HttpSession session,
                                     Model model) {
        User loginUser = HttpSessionUtils.getUserFromSession(session).orElseThrow(LoginRequiredException::new);
        Question question = questionRepository.findById(questionId).orElseThrow(QuestionNotFoundException::new);
        Answer answer = answerRepository.findByQuestionIdAndId(questionId, answerId).orElseGet(Answer::new);

        if (!loginUser.equals(answer.getWriter())) {
            return "redirect:/questions/" + questionId;
        }

        model.addAttribute("question", question);
        model.addAttribute("answer", answer);

        return "questions/answer-modify-form";
    }

    @PutMapping("/{answerId}")
    public String updateAnswer(@PathVariable Long questionId,
                               @PathVariable Long answerId,
                               @RequestParam String comment,
                               HttpSession session) {
        User loginUser = HttpSessionUtils.getUserFromSession(session).orElseThrow(LoginRequiredException::new);
        Answer answer = answerRepository.findByQuestionIdAndId(questionId, answerId).orElseGet(Answer::new);
        if (!loginUser.equals(answer.getWriter())) {
            return "redirect:/questions/" + questionId;
        }
        answer.setComment(comment);
        answer.setUpdatedDateTime(LocalDateTime.now());
        answerRepository.save(answer);

        return "redirect:/questions/" + questionId;
    }

}
