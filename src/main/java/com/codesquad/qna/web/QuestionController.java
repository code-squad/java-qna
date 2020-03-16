package com.codesquad.qna.web;

import com.codesquad.qna.domain.Question;
import com.codesquad.qna.domain.QuestionRepository;
import com.codesquad.qna.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/questions/form")
    public String questionForm(HttpSession session, Model model) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login-form";
        }

        User writer = HttpSessionUtils.getUserFromSession(session);
        model.addAttribute("userName", writer.getUserName());

        return "/qna/form";
    }
    @PostMapping("/questions")
    public String writeQuestion(Question question, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login-form";
        }

        DatabaseUtils.replaceEscapesToTags(question);
        questionRepository.save(question);

        return "redirect:/";
    }

    @GetMapping("")
    public String home(Model model) {
        model.addAttribute("questions", questionRepository.findAll());

        return "index";
    }

    @GetMapping("/questions/{id}")
    public String showQuestion(@PathVariable Long id, Model model) {
        Question selectedQuestion = questionRepository.findById(id).orElseThrow(QuestionNotFoundException::new);
        model.addAttribute("question", selectedQuestion);

        return "qna/show";
    }

    @GetMapping("/questions/{id}/form")
    public String questionUpdateForm(@PathVariable Long id, Model model, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login-form";
        }

        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        Question selectedQuestion = questionRepository.findById(id).orElseThrow(QuestionNotFoundException::new);

        if (!selectedQuestion.isSameWriter(sessionedUser)) {
            return "redirect:/users/login-form";
        }

        DatabaseUtils.replaceTagsToEscapes(selectedQuestion);
        model.addAttribute("question", selectedQuestion);

        return "qna/updateForm";
    }

    @PutMapping("/questions/{id}")
    public String updateQuestion(@PathVariable Long id, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login-form";
        }

        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        Question selectedQuestion = questionRepository.findById(id).orElseThrow(QuestionNotFoundException::new);

        if (!selectedQuestion.isSameWriter(sessionedUser)) {
            return "redirect:/users/login-form";
        }

        DatabaseUtils.replaceEscapesToTags(selectedQuestion);
        questionRepository.save(selectedQuestion);

        return "redirect:/questions/{id}";
    }
}
