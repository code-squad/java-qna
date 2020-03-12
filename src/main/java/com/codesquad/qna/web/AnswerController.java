package com.codesquad.qna.web;

import com.codesquad.qna.global.error.exception.DataNotFoundException;
import com.codesquad.qna.global.error.exception.ErrorCode;
import com.codesquad.qna.global.error.exception.RequestNotAllowedException;
import com.codesquad.qna.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class AnswerController {

    private static final Logger log = LoggerFactory.getLogger(AnswerController.class);
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("")
    public Answer create(@PathVariable Long questionId, String contents, @RequestAttribute User sessionedUser) {
        log.info("api create : {} ", contents);
        Question answeredQuestion = findQuestion(questionId);
        Answer newAnswer = new Answer(answeredQuestion, sessionedUser, contents);
        return answerRepository.save(newAnswer);
    }

    @DeleteMapping("/{answerId}")
    public Result delete(@PathVariable Long questionId, @PathVariable Long answerId, @RequestAttribute User sessionedUser) {
        Answer answer = findAnswer(answerId);
        if (!answer.matchWriter(sessionedUser))
            return Result.fail("삭제 권한이 없습니다.");

        answer.setDeleted(true);
        answerRepository.save(answer);
        return Result.ok();
    }

    private Question findQuestion(Long questionId) {
        return questionRepository.findById(questionId).orElseThrow(() -> new DataNotFoundException(ErrorCode.DATA_NOT_FOUND));
    }

    private Answer findAnswer(Long answerId) {
        return answerRepository.findById(answerId).orElseThrow(() -> new DataNotFoundException(ErrorCode.DATA_NOT_FOUND));
    }
}
