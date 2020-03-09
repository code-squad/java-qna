package com.codesquad.qna.web;

import com.codesquad.qna.domain.Answer;
import com.codesquad.qna.domain.AnswerRepository;
import com.codesquad.qna.domain.Question;
import com.codesquad.qna.domain.QuestionRepository;
import com.codesquad.qna.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;

import static com.codesquad.qna.web.HttpSessionUtils.getUserFromSession;
import static com.codesquad.qna.web.HttpSessionUtils.isLoginUser;
import static com.codesquad.qna.web.UserController.REDIRECT_LOGIN_FORM;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("")
    public String answer(@PathVariable Long questionId, HttpSession session, String contents) {
        if (!isLoginUser(session)) {
            return REDIRECT_LOGIN_FORM;
        }

        User sessionedUser = getUserFromSession(session);
        Question question = questionRepository.findById(questionId).orElseThrow(EntityNotFoundException::new);
        Answer answer = new Answer(sessionedUser, question, contents);
        answerRepository.save(answer);
        return "redirect:/questions/{questionId}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        Answer answer = answerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        answerRepository.delete(answer);
        return "redirect:/questions/{question.id}";
    }
}
