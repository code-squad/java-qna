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
    public String deleteAnswer(@PathVariable Long id, Long questionId, HttpSession session) {
//        if (!HttpSessionUtils.isLogin(session)) {
//            return "/users/loginForm";
//        }
//        Answer answer = answerRepository.getOne(id);
//        answerRepository.delete(answer);
//        return "redirect:/questions/" + questionId;
        return "/";
    }
}
