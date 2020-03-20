package com.codesquad.qna.web;

import com.codesquad.qna.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class AnswerController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping("/questions/{questionId}/answers")
    public String write(@PathVariable Long questionId, Answer answer, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login-form";
        }

        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        Question selectedQuestion = questionRepository.findById(questionId).orElseThrow(QuestionNotFoundException::new);
        answer.setWriter(sessionedUser);
        answer.setQuestion(selectedQuestion);
        answerRepository.save(answer);

        return "redirect:/questions/{questionId}";
    }

    @DeleteMapping("/questions/{questionId}/answers/{id}")
    public String delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login-form";
        }

        Answer selectedAnswer = answerRepository.findById(id).orElseThrow(QuestionNotFoundException::new);
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);

        if (!selectedAnswer.isSameWriter(sessionedUser)) {
            return "redirect:/users/login-form"; // 권한이 없습니다.
        }

        answerRepository.delete(selectedAnswer);

        return "redirect:/questions/{questionId}";
    }
}
