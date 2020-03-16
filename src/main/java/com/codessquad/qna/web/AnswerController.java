package com.codessquad.qna.web;

import com.codessquad.qna.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    QuestionRepository questionRepository;

    @PostMapping("")
    public String create(@PathVariable("questionId") Long questionId, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoggedInUser(session)) {
            return "redirect:/users/login";
        }

        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(questionId).orElseThrow(NoSuchElementException::new);

        Answer answer = new Answer(loginUser, question, contents);
        answerRepository.save(answer);

        return String.format("redirect:/questions/%d", questionId);
    }

    @DeleteMapping("/{answerId}")
    public String delete(@PathVariable("questionId") Long questionId, @PathVariable("answerId") Long answerId, HttpSession session) {
        if (!HttpSessionUtils.isLoggedInUser(session)) {
            return "redirect:/users/login";
        }

        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Answer answer = answerRepository.findById(answerId).orElseThrow(NoSuchElementException::new);

        if (!loginUser.getUserId().equals(answer.getWriter().getUserId())) {
            throw new IllegalStateException("자기 자신의 질문만 삭제 가능합니다.");
        }

        answerRepository.delete(answer);

        return String.format("redirect:/questions/%d", questionId);
    }

    @GetMapping("/{answerId}/form")
    public String modifyAnswer(@PathVariable("questionId") Long questionId, @PathVariable("answerId") Long answerId, HttpSession session, Model model) {
        if (!HttpSessionUtils.isLoggedInUser(session)) {
            return "redirect:/users/login";
        }

        model.addAttribute("answers", answerRepository.findById(answerId));

        return "/qna/updateAnswerForm";
    }
}
