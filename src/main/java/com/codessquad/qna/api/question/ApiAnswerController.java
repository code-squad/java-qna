package com.codessquad.qna.api.question;

import com.codessquad.qna.api.result.Result;
import com.codessquad.qna.common.error.exception.LoginRequiredException;
import com.codessquad.qna.common.utils.HttpSessionUtils;
import com.codessquad.qna.web.question.*;
import com.codessquad.qna.web.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping("")
    public Answer create(@PathVariable Long questionId, String comment, HttpSession session) {
        User loginUser = HttpSessionUtils.getUserFromSession(session).orElseThrow(LoginRequiredException::new);
        Question question = questionRepository.findById(questionId).orElse(null);
        Answer answer = new Answer(loginUser, question, comment);
        return answerRepository.save(answer);
    }

    @DeleteMapping("/{answerId}")
    public Result deleteAnswer(@PathVariable Long questionId, @PathVariable Long answerId, HttpSession session) {
        User loginUser = HttpSessionUtils.getUserFromSession(session).orElseThrow(LoginRequiredException::new);
        Answer answer = answerRepository.findByQuestionIdAndId(questionId, answerId).orElseGet(Answer::new);
        if (!loginUser.equals(answer.getWriter())) {
            return Result.failed("해당 댓글의 작성자가 아닙니다.");
        }
        return Result.ok(answerRepository.save(answer.delete()));
    }
}
