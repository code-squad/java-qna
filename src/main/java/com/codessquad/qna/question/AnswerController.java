package com.codessquad.qna.question;

import com.codessquad.qna.user.User;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

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
        Object loginUserAttribute = session.getAttribute("loginUser");
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

    @GetMapping("/questions/{questionId}/answers/{answerId}")
    public String goUpdateAnswerForm(@PathVariable Long questionId,
                                     @PathVariable Long answerId,
                                     Answer answer) {
        return "redirect:/questions/" + questionId;
    }

    @PutMapping("/questions/{questionId}/answers/{answerId}/form")
    public String updateAnswer(@PathVariable Long questionId,
                               @PathVariable Long answerId,
                               Answer answer) {
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
