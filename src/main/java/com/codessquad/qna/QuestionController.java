package com.codessquad.qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;

@Controller
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/qna/form")
    public String viewQuestionForm(HttpSession session) {
        if (!HttpSessionUtils.isLogin(session)) {
            return "/users/loginForm";
        }
        return "/qna/form";
    }

    @PostMapping("/questions")
    public String createQuestion(String title, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLogin(session)) {
            return "/users/loginForm";
        }
        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        Question question = new Question(sessionUser, title, contents);
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String viewQnaList(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "/index";
    }

    @GetMapping("/questions/{id}")
    public String viewQuestionContents(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("question", questionRepository.findById(id).get());
            return "/qna/show";
        } catch (NoSuchElementException e) {
            return e.getMessage();
        }
    }

    @GetMapping("/questions/{id}/form")
    public String viewUpdatedForm(@PathVariable Long id, Model model, HttpSession session) {
        try {
            model.addAttribute("question", getSessionQuestion(id, session));
            return "/qna/updatedForm";
        } catch (NullPointerException | IllegalAccessException | NoSuchElementException e) {
            return e.getMessage();
        }
    }

    @PutMapping("/questions/{id}")
    public String updateQuestion(@PathVariable Long id, String title, String contents, HttpSession session) {
        try {
            Question question = getSessionQuestion(id, session);
            question.update(title, contents);
            questionRepository.save(question);
            return "redirect:/questions/" + id;
        } catch (NullPointerException | IllegalAccessException | NoSuchElementException e) {
            return e.getMessage();
        }
    }

    private Question getSessionQuestion(Long id, HttpSession session) throws IllegalAccessException {
        if (!HttpSessionUtils.isLogin(session)) {
            throw new NullPointerException();
        }
        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(id).get();
        if (!question.isWriterEquals(sessionUser)) {
            throw new IllegalAccessException();
        }
        return question;
    }
}
