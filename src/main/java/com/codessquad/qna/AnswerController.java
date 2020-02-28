package com.codessquad.qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("")
    public String createAnswer(@PathVariable Long questionId, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLogin(session)) {
            return "/users/loginForm";
        }
        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(questionId).get();
        Answer answer = new Answer(sessionUser, question, contents);
        answerRepository.save(answer);
        return "redirect:/questions/" + questionId;
    }

    @DeleteMapping("/{id}")
    public String deleteAnswer(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        try {
            Answer answer = getVerifiedAnswer(id, session);
            answerRepository.delete(answer);
            return "redirect:/questions/" + questionId;
        } catch (NullPointerException | IllegalAccessException e) {
            System.out.println("ERROR CODE > " + e.toString());
            return e.getMessage();
        }
    }

    private Answer getVerifiedAnswer(Long id, HttpSession session) throws IllegalAccessException {
        //checkNotFound(id);
        if (!HttpSessionUtils.isLogin(session)) {
            throw new NullPointerException("/error/unauthorized");
        }
        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        Answer answer = answerRepository.getOne(id);
        if (!answer.isWriterEquals(sessionUser)) {
            throw new IllegalAccessException("/error/unauthorized");
        }
        return answer;
    }
}
