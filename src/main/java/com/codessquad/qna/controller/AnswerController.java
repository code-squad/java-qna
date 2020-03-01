package com.codessquad.qna.controller;

import com.codessquad.qna.repository.*;
import com.codessquad.qna.util.HttpSessionUtil;
import com.codessquad.qna.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    QuestionRepository questionRepository;

    @GetMapping
    public Object showAnswerList(@PathVariable Long questionId, Model model) {
        ArrayList<Answer> answerList = answerRepository.findByQuestionId(questionId);
        model.addAttribute("answers", answerList);
        return ResponseEntity.ok(model);
    }

    @GetMapping("/{answerId}/editForm")
    public String showEditForm(@PathVariable("questionId") Long questionId, @PathVariable("answerId") Long answerId, HttpSession session, Model model) {
        if (!HttpSessionUtil.isAuthorizedUser(session)) {
            return PathUtil.UNAUTHORIZED;
        }
        Optional<Question> question = questionRepository.findById(questionId);
        if (!question.isPresent()) {
            return PathUtil.NOT_FOUND;
        }

        Optional<Answer> answer = answerRepository.findById(answerId);
        if (!answer.isPresent()) {
            return PathUtil.NOT_FOUND;
        }

        User user = HttpSessionUtil.getUserFromSession(session);
        if (!answer.get().isCorrectWriter(user)) {
            return PathUtil.UNAUTHORIZED;
        }
        model.addAttribute("answer", answer.get());
        model.addAttribute("question", question.get());
        return "answer/edit";
    }

    @PostMapping
    public Object createAnswer(@PathVariable Long questionId, @RequestBody Answer answer, HttpSession session) {
        if (!HttpSessionUtil.isAuthorizedUser(session)) {
            return PathUtil.UNAUTHORIZED;
        }

        Optional<Question> question = questionRepository.findById(questionId);
        if (!question.isPresent()) {
            return PathUtil.NOT_FOUND;
        }

        User user = HttpSessionUtil.getUserFromSession(session);
        answer.setWriter(user);
        answer.setQuestion(question.get());
        if (!answer.isCorrectFormat(answer)) {
            return PathUtil.BAD_REQUEST;
        }
        answerRepository.save(answer);
        return PathUtil.REDIRECT_QUESTION_DETAIL + questionId;
    }

    @DeleteMapping("/{answerId}")
    public String deleteAnswer(@PathVariable("questionId") Long questionId, @PathVariable("answerId") Long answerId, HttpSession session) {
        if (!HttpSessionUtil.isAuthorizedUser(session)) {
            return PathUtil.UNAUTHORIZED;
        }
        Optional<Question> question = questionRepository.findById(questionId);
        if (!question.isPresent()) {
            return PathUtil.NOT_FOUND;
        }

        Optional<Answer> answer = answerRepository.findById(answerId);
        if (!answer.isPresent()) {
            return PathUtil.NOT_FOUND;
        }

        User user = HttpSessionUtil.getUserFromSession(session);
        if (!answer.get().isCorrectWriter(user)) {
            return PathUtil.UNAUTHORIZED;
        }
        answerRepository.delete(answer.get());
        return PathUtil.REDIRECT_QUESTION_DETAIL + questionId;
    }

    @PutMapping("/{answerId}")
    public String updateAnswer(@PathVariable("questionId") Long questionId, @PathVariable("answerId") Long answerId, String contents ,HttpSession session) {
        if (!HttpSessionUtil.isAuthorizedUser(session)) {
            return PathUtil.UNAUTHORIZED;
        }
        Optional<Question> question = questionRepository.findById(questionId);
        if (!question.isPresent()) {
            return PathUtil.NOT_FOUND;
        }

        Optional<Answer> answer = answerRepository.findById(answerId);
        if (!answer.isPresent()) {
            return PathUtil.NOT_FOUND;
        }

        User user = HttpSessionUtil.getUserFromSession(session);
        if (!answer.get().isCorrectWriter(user)) {
            return PathUtil.UNAUTHORIZED;
        }
        Answer updateData = new Answer(user, question.get(), contents);
        answer.get().update(updateData);
        answerRepository.save(answer.get());
        return PathUtil.REDIRECT_QUESTION_DETAIL + questionId;
    }
}
