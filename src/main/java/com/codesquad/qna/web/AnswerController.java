package com.codesquad.qna.web;

import com.codesquad.qna.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping("")
    public String create(@PathVariable Long questionId, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/user/login_failed";
        }
        User loginUser = HttpSessionUtils.getUserFromSession(session).orElse(null);
        Question question = questionRepository.getOne(questionId);
        Answer answer = new Answer(loginUser, question, contents);
        answerRepository.save(answer);
        return String.format("redirect:/questions/%d", questionId);
    }

    @DeleteMapping("/{answerId}")
    public String delete(@PathVariable Long questionId, @PathVariable Long answerId, HttpSession session) {
        Answer answer = answerRepository.getOne(answerId);
        User loginUser = HttpSessionUtils.getUserFromSession(session).orElse(null);
        if (answer.authorizeUser(loginUser)) {
            answerRepository.deleteById(answerId);
            return String.format("redirect:/questions/%d", questionId);
        }
        return HomeController.NOT_AUTHORIZE_DIRECTORY;
    }
}
