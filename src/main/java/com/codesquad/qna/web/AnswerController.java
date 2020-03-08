package com.codesquad.qna.web;

import com.codesquad.qna.domain.*;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
    @Autowired
    private QuestionRepostory questionRepostory;

    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping("")
    public String create(@PathVariable Long questionId, String comment, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }

        try {
            User sessionUser = HttpSessionUtils.getUserFromSession(session);
            Question currentQuestion = questionRepostory.findById(questionId).orElseThrow(() -> new NotFoundException("그런 게시물 없어요"));
            Answer answer = new Answer(sessionUser, currentQuestion, comment);
            currentQuestion.increaseAnswersCount();
            answerRepository.save(answer);
            return String.format("redirect:/questions/%d", questionId); //답변을 단 게시물로 redirect
        } catch (NotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{answerId}")
    public String removeAnswer(@PathVariable Long questionId, @PathVariable Long answerId, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }

        try {
            User sessionUser = HttpSessionUtils.getUserFromSession(session);
            Question currentQuestion = questionRepostory.findById(questionId).orElseThrow(() -> new NotFoundException("그런 게시글 없어요"));
            Answer currentAnswer = answerRepository.findById(answerId).orElseThrow(() -> new NotFoundException("그런 댓글 없어요"));

            if (!currentAnswer.matchUser(sessionUser)) {
                throw new IllegalStateException("본인 댓글만 삭제할 수 있어요");
            }

            currentQuestion.reduceAnswersCount();
            answerRepository.delete(currentAnswer);
            return "redirect:/questions/{questionId}";
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
