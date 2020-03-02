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
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/form")
    public String form(HttpSession httpSession) {
        User user = HttpSessionUtils.getUserFromSession(httpSession);

        if (!HttpSessionUtils.isLoginUser(user)) {
            return "redirect:/users/loginForm";
        }
        return "question/questionForm";
    }

    @PostMapping("")
    public String question(String contents, String title, HttpSession httpSession) {
        User user = HttpSessionUtils.getUserFromSession(httpSession);

        if (!HttpSessionUtils.isLoginUser(user)) {
            return "redirect:/users/loginForm";
        }
        Question question = new Question(user.getName(), title, contents);
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String questionPage(@PathVariable Long id, Model model) {
        model.addAttribute("question", findQuestion(questionRepository, id));
        return "question/show";
    }

    private Object findQuestion(QuestionRepository questionRepository, Long id) {
        return questionRepository.findById(id).orElseThrow(() ->
                new IllegalStateException("There is no question."));
    }
}
