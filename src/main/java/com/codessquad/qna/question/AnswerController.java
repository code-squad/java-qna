package com.codessquad.qna.question;

import com.codessquad.qna.constants.CommonConstants;
import com.codessquad.qna.error.exception.QuestionNotFoundException;
import com.codessquad.qna.user.User;
import com.codessquad.qna.utils.HttpSessionUtils;
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
        User loginUser = HttpSessionUtils.getUserFromSession(session).orElse(null);
        if (loginUser == null) {
            return CommonConstants.REDIRECT_LOGIN_PAGE;
        }

        Question question = questionRepository.findById(questionId).orElseThrow(() -> new QuestionNotFoundException("해당 질문글이 존재하지 않습니다."));
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
        User loginUser = HttpSessionUtils.getUserFromSession(session).orElse(null);
        if (loginUser == null) {
            return CommonConstants.REDIRECT_LOGIN_PAGE;
        }

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
