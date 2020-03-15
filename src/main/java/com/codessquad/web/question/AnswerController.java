package com.codessquad.web.question;

import com.codessquad.common.HttpSessionUtils;
import com.codessquad.domain.qna.Answer;
import com.codessquad.domain.qna.AnswerRepository;
import com.codessquad.domain.qna.Question;
import com.codessquad.domain.qna.QuestionRepository;
import com.codessquad.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{id}/answers")
public class AnswerController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping("")
    public String creat(@PathVariable Long id, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(id).get();
        Answer answer = new Answer(loginUser, question, contents);
        answerRepository.save(answer);
        return String.format("redirect:/questions/%d", id);
    }

    @GetMapping("/{answerId}/form")
    public String updateForm(@PathVariable Long answerId, @PathVariable Long id, Model model, HttpSession session) {
        try {
            Answer currentAnswer = answerRepository.findById(answerId).get();
            hasPermission(session, currentAnswer);
            model.addAttribute("answer", currentAnswer);
            return "/qna/answerUpdateForm";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/user/login";
        }
    }

    @PutMapping("/{answerId}")
    public String update(@PathVariable Long answerId, @PathVariable Long id, Answer updateAnswer, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }
        Answer currentAnswer = answerRepository.findById(answerId).get();
        currentAnswer.update(updateAnswer);
        answerRepository.save(currentAnswer);
        return String.format("redirect:/questions/%d", id);
    }

    @DeleteMapping("/{answerId}")
    public String delete(@PathVariable Long answerId, @PathVariable Long id, HttpSession session, Model model) {
        try {
            Answer currentAnswer = answerRepository.findById(answerId).get();
            hasPermission(session, currentAnswer);
            answerRepository.delete(currentAnswer);
            return String.format("redirect:/questions/%d", id);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "user/login";
        }
    }

    private boolean hasPermission(HttpSession session, Answer answer) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        if (!answer.matchUser(loginUser)) {
            throw new IllegalStateException("자신이 쓴 댓글만 수정, 삭제가 가능합니다.");
        }
        return true;
    }
}
