package com.codesquad.qna.web;

import com.codesquad.qna.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class AnswerController {

    private static final Logger log = LoggerFactory.getLogger(AnswerController.class);
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("/questions/{questionId}/answers/")
    public String create(@PathVariable Long questionId, String contents, HttpSession session) {
        if (HttpSessionUtils.isNotLogined(session))
            return "redirect:/user/login";

        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        Question answeredQuestion = findQuestion(questionId);
        Answer newAnswer = new Answer(answeredQuestion, sessionedUser, contents);
        answerRepository.save(newAnswer);
        return String.format("redirect:/questions/%d", questionId);
    }

    @DeleteMapping("/questions/{questionId}/answers/{answerId}")
    public String delete(@PathVariable Long questionId, @PathVariable Long answerId, HttpSession session) {
        if (HttpSessionUtils.isNotLogined(session))
            return "redirect:/user/login";

        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        Answer answer = findAnswer(answerId);
        if (!answer.matchWriter(sessionedUser))
            throw new IllegalStateException(Error.ILLEGAL_STATE.getMessage());

        answerRepository.delete(answer);
        return String.format("redirect:/questions/%d", questionId);
    }

    private Question findQuestion(Long questionId) {
        return questionRepository.findById(questionId).orElseThrow(()->new IllegalArgumentException(Error.ILLEGAL_ARGUMENT.getMessage()));
    }

    private Answer findAnswer(Long answerId) {
        return answerRepository.findById(answerId).orElseThrow(()->new IllegalArgumentException(Error.ILLEGAL_ARGUMENT.getMessage()));
    }
}
