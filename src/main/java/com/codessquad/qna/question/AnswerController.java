package com.codessquad.qna.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AnswerController {
    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping("/questions/{questionId}/answers")
    public String createAnswer(@PathVariable Long questionId, Answer answer) {
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
