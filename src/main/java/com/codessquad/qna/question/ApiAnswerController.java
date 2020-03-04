package com.codessquad.qna.question;

import com.codessquad.qna.user.User;
import com.codessquad.qna.utils.HttpSessionUtils;
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
        User loginUser = HttpSessionUtils.getUserFromSession(session).orElse(null);
        if (loginUser == null) {
            return null;
        }

        Question question = questionRepository.findById(questionId).orElse(null);
        Answer answer = new Answer(loginUser, question, comment);
        return answerRepository.save(answer);
    }

    @DeleteMapping("/{answerId}")
    public Result deleteAnswer(@PathVariable Long questionId, @PathVariable Long answerId, HttpSession session) {
        User loginUser = HttpSessionUtils.getUserFromSession(session).orElse(null);
        if (loginUser == null) {
            return Result.failed("로그인이 되어있지 않습니다.");
        }

        Answer answer = answerRepository.findByQuestionIdAndId(questionId, answerId).orElse(null);
        if (!loginUser.equals(answer.getWriter())) {
            return Result.failed("해당 댓글의 작성자가 아닙니다.");
        }
        return Result.ok(answerRepository.save(answer.delete()));
    }
}
