package com.codessquad.qna;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.QuestionRepository;
import com.codessquad.qna.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("questions/form")
    public String form(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/users/loginForm";
        }

        return "/question/form";
    }

    @PostMapping("/questions")
    public String create(String title, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/users/loginForm";
        }

        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        if (sessionUser != null) {
            Question newQuestion = new Question(sessionUser.getUserId(), title, contents);
            questionRepository.save(newQuestion);
        }

        return "redirect:/";
    }

    @GetMapping("/questions/{id}")
    public String show(Model model, @PathVariable Long id) {
        model.addAttribute("question", questionRepository.findById(id).orElse(null));

        return "/question/show";
    }
}
