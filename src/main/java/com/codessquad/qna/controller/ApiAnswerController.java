package com.codessquad.qna.controller;

import com.codessquad.qna.exception.NoSuchElementException;
import com.codessquad.qna.exception.UnauthorizedException;
import com.codessquad.qna.exception.WrongFormatException;
import com.codessquad.qna.repository.Answer;
import com.codessquad.qna.repository.AnswerRepository;
import com.codessquad.qna.repository.Question;
import com.codessquad.qna.repository.QuestionRepository;
import com.codessquad.qna.util.ErrorMessageUtil;
import com.codessquad.qna.util.HttpSessionUtil;
import com.codessquad.qna.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("api/questions/{questionId}/answers")
public class ApiAnswerController {
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    QuestionRepository questionRepository;

    @PostMapping
    public Answer createAnswer(@PathVariable Long questionId, @RequestBody Answer answer, HttpSession session) {
        Question question = questionRepository.findById(questionId).orElseThrow(() ->
                new NoSuchElementException(PathUtil.NOT_FOUND, ErrorMessageUtil.NOTFOUND_QUESTION));
        Answer newAnswer = new Answer(HttpSessionUtil.getUserFromSession(session), question, answer.getContents());

        if (!newAnswer.isCorrectFormat(newAnswer)) {
            throw new WrongFormatException(PathUtil.BAD_REQUEST, ErrorMessageUtil.WRONG_FORMAT);
        }
        return answerRepository.save(newAnswer);
    }

    @DeleteMapping("/{answerId}")
    public void deleteAnswer(@PathVariable("questionId") Long questionId, @PathVariable("answerId") Long answerId, HttpSession session) {
        questionRepository.findById(questionId).orElseThrow(() ->
                new NoSuchElementException(PathUtil.NOT_FOUND, ErrorMessageUtil.NOTFOUND_QUESTION));
        Answer answer = answerRepository.findById(answerId).orElseThrow(() ->
                new NoSuchElementException(PathUtil.NOT_FOUND, ErrorMessageUtil.NOTFOUND_ANSWER));

        if (!answer.isCorrectWriter(HttpSessionUtil.getUserFromSession(session)))
            throw new UnauthorizedException(PathUtil.UNAUTHORIZED, ErrorMessageUtil.UNAUTHORIZED);

        answerRepository.delete(answerId);
    }
}
