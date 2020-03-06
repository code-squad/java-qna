package com.codessquad.qna;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;

import static com.codessquad.qna.HttpSessionUtils.isLogin;
import static com.codessquad.qna.HttpSessionUtils.getUserFromSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/form")
    public String viewQuestionForm(HttpSession session) {
        if (!isLogin(session)) {
            return "/users/loginForm";
        }
        return "/qna/form";
    }

    @PostMapping("")
    public String createQuestion(String title, String contents, HttpSession session) {
        if (!isLogin(session)) {
            return "/users/loginForm";
        }
        User sessionUser = getUserFromSession(session);
        Question question = new Question(sessionUser, title, contents);
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String viewQuestionContents(@PathVariable Long id, Model model) {
        try {
            Question question = findQuestion(id);
            model.addAttribute("question", question);
            return "/qna/show";
        } catch (NoSuchElementException e) {
            log.info("Error Code > " + e.toString());
            return e.getMessage();
        }
    }

    @GetMapping("/{id}/form")
    public String viewUpdatedForm(@PathVariable Long id, Model model, HttpSession session) {
        try {
            model.addAttribute("question", getVerifiedQuestion(id, session));
            return "/qna/updatedForm";
        } catch (NullPointerException | IllegalAccessException | NoSuchElementException e) {
            log.info("Error Code > " + e.toString());
            return e.getMessage();
        }
    }

    @PutMapping("/{id}")
    public String updateQuestion(@PathVariable Long id, String title, String contents, HttpSession session) {
        try {
            Question question = getVerifiedQuestion(id, session);
            question.update(title, contents);
            questionRepository.save(question);
            return "redirect:/questions/" + id;
        } catch (NullPointerException | IllegalAccessException | NoSuchElementException e) {
            log.info("Error Code > " + e.toString());
            return e.getMessage();
        }
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable Long id, HttpSession session) {
        try {
            Question question = getVerifiedQuestion(id, session);
            questionRepository.delete(question);
            return "redirect:/";
        } catch (NullPointerException | IllegalAccessException | NoSuchElementException e) {
            log.info("Error Code > " + e.toString());
            return e.getMessage();
        }
    }

    private Question findQuestion(Long id) {
        return questionRepository.findById(id).orElseThrow(() -> new NoSuchElementException("/error/notFound"));
    }

    private Question getVerifiedQuestion(Long id, HttpSession session) throws IllegalAccessException {
        if (!isLogin(session)) {
            throw new NullPointerException("/error/unauthorized");
        }
        User sessionUser = getUserFromSession(session);
        Question question = findQuestion(id);
        if (!question.isWriterEquals(sessionUser)) {
            throw new IllegalAccessException("/error/unauthorized");
        }
        return question;
    }
}
