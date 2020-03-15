package com.codessquad.web.question;

import com.codessquad.common.HttpSessionUtils;
import com.codessquad.domain.qna.Question;
import com.codessquad.domain.qna.QuestionRepository;
import com.codessquad.domain.user.User;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Queue;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("")
    public String main(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "/main";
    }

    @GetMapping("/form")
    public String form(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/users/loginForm";
        }
        return "/qna/form";
    }

    @PostMapping("")
    public String create(String title, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/users/loginForm";
        }
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Question newQuestion = new Question(loginUser, title, contents);
        questionRepository.save(newQuestion);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionRepository.findById(id).orElseThrow(() -> new IllegalStateException("존재하지 않는 질문 입니다.")));
        return "qna/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        try {
            Question currentQuestion = questionRepository.findById(id).get();
            hasPermission(session, currentQuestion);
            model.addAttribute("question", currentQuestion);
            return "/qna/questionUpdateForm";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/user/login";
        }
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, Question updateQuestion, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }
        Question currentQuestion = questionRepository.findById(id).get();
        currentQuestion.update(updateQuestion);
        questionRepository.save(currentQuestion);
        return String.format("redirect:/questions/%d", id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession session, Model model) {
        try {
            Question currentQuestion = questionRepository.findById(id).get();
            hasPermission(session, currentQuestion);
            questionRepository.delete(currentQuestion);
            return "redirect:/questions";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/user/login";
        }
    }

    private boolean hasPermission(HttpSession session, Question question) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        if (!question.matchUser(loginUser)) {
            throw new IllegalStateException("자신이 쓴 글만 수정, 삭제가 가능합니다.");
        }
        return true;
    }
}
