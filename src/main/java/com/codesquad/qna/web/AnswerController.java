package com.codesquad.qna.web;

import com.codesquad.qna.global.error.exception.DataNotFoundException;
import com.codesquad.qna.global.error.exception.ErrorCode;
import com.codesquad.qna.global.error.exception.RequestNotAllowedException;
import com.codesquad.qna.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

    private static final Logger log = LoggerFactory.getLogger(AnswerController.class);
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("")
    public String create(@PathVariable Long questionId, String contents, @RequestAttribute User sessionedUser) {
        Question answeredQuestion = findQuestion(questionId);
        Answer newAnswer = new Answer(answeredQuestion, sessionedUser, contents);
        answerRepository.save(newAnswer);
        return String.format("redirect:/questions/%d", questionId);
    }

    @DeleteMapping("/{answerId}")
    public String delete(@PathVariable Long questionId, @PathVariable Long answerId, @RequestAttribute User sessionedUser) {
        Answer answer = findAnswer(answerId);
        if (!answer.matchWriter(sessionedUser))
            throw new RequestNotAllowedException(ErrorCode.FORBIDDEN);

        answer.setDeleted(true);
        answerRepository.save(answer);
        return String.format("redirect:/questions/%d", questionId);
    }

    private Question findQuestion(Long questionId) {
        return questionRepository.findById(questionId).orElseThrow(() -> new DataNotFoundException(ErrorCode.DATA_NOT_FOUND));
    }

    private Answer findAnswer(Long answerId) {
        return answerRepository.findById(answerId).orElseThrow(() -> new DataNotFoundException(ErrorCode.DATA_NOT_FOUND));
    }
}
