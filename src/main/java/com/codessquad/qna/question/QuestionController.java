package com.codessquad.qna.question;

import com.codessquad.qna.utils.HttpSessionUtils;
import com.codessquad.qna.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    private Logger log = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/form")
    public String qnaForm(HttpSession session) {
        if (HttpSessionUtils.isNoneExistentUser(session)) {
            return "redirect:/users/loginForm";
        }

        return "qna/form";
    }

    @PostMapping("")
    public String createQuestion(String title, String contents, HttpSession session) {
        if (HttpSessionUtils.isNoneExistentUser(session)) {
            return "redirect:/users/loginForm";
        }

        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        Question newQuestion = new Question(sessionUser, title, contents);
        questionRepository.save(newQuestion);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String showQuestionContents(@PathVariable Long id, Model model) {
        model.addAttribute("question",
                questionRepository.findById(id).orElseThrow(IllegalStateException::new));
        return "qna/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        if (HttpSessionUtils.isNoneExistentUser(session)) {
            return "redirect:/users/loginForm";
        }

        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(id).orElseThrow(IllegalStateException::new);
        if (question.isNotSameWriter(loginUser)) {
            return "redirect:/users/loginForm";
        }

        model.addAttribute("question", question);
        return "qna/updateForm";
    }

    @PutMapping("/{id}")
    public String updateQuestion(@PathVariable Long id, String title, String contents, HttpSession session) {
        if (HttpSessionUtils.isNoneExistentUser(session)) {
            return "redirect:/users/loginForm";
        }

        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(id).orElseThrow(IllegalStateException::new);
        if (question.isNotSameWriter(loginUser)) {
            return "redirect:/users/loginForm";
        }

        question.update(title, contents);
        questionRepository.save(question);
        return String.format("redirect:/questions/%d",id);
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable Long id, HttpSession session) {
        if (HttpSessionUtils.isNoneExistentUser(session)) {
            return "redirect:/users/loginForm";
        }

        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(id).orElseThrow(IllegalStateException::new);
        if (question.isNotSameWriter(loginUser)) {
            return "redirect:/users/loginForm";
        }

        questionRepository.deleteById(id);
        return "redirect:/";
    }
}
