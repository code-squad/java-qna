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
    public String writeQuestion(Question question, Model model, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login-form";
        }
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
}
