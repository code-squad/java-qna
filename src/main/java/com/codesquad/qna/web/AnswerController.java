package com.codesquad.qna.web;

import com.codesquad.qna.global.error.exception.DataNotFoundException;
import com.codesquad.qna.global.error.exception.ErrorCode;
import com.codesquad.qna.global.error.exception.RequestNotAllowedException;
import com.codesquad.qna.model.Answer;
import com.codesquad.qna.model.AnswerRepository;
import com.codesquad.qna.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

    private static final Logger log = LoggerFactory.getLogger(AnswerController.class);
    @Autowired
    private AnswerRepository answerRepository;

    @PutMapping("/{answerId}")
    public String update(@PathVariable Long questionId, @PathVariable Long answerId, String contents, @RequestAttribute User sessionedUser) {
        Answer answer = answerRepository.findById(answerId).orElseThrow(() -> new DataNotFoundException(ErrorCode.DATA_NOT_FOUND));
        if (!answer.matchWriter(sessionedUser))
            throw new RequestNotAllowedException(ErrorCode.FORBIDDEN);
        answer.update(contents);
        answerRepository.save(answer);
        log.debug("answer update : {}", contents);
        return String.format("redirect:/questions/%d", questionId);
    }
}
