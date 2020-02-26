package com.codessquad.qna;

import com.codesquad.web.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("/qna/form")
    public String question(Question question, HttpSession session) {
        if (!HttpSessionUtils.isUserLogin(session)) {
            return "redirect:/login";
        }

        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "index";
    }

    @GetMapping("/questions/{id}")
    public String post(@PathVariable("id") Long id, Model model) {
        Optional optionalQuestion = questionRepository.findById(id);

        if (!optionalQuestion.isPresent()) {
            return "redirect:/";
        }

        model.addAttribute("question", optionalQuestion.get());
        return "qna/show";
    }

    @GetMapping("/question")
    public String createQuestion(HttpSession session, Model model) {
        if (!HttpSessionUtils.isUserLogin(session)) {
            return "redirect:/login";
        }

        model.addAttribute("writer", HttpSessionUtils.getUserFromSession(session).getName());
        return "/qna/form";
    }
}
