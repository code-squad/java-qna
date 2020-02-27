package com.codessquad.qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @GetMapping("/form")
    public String viewQuestionForm(HttpSession session) {
        if (!HttpSessionUtils.isLogin(session)) {
            return "/users/loginForm";
        }
        return "/qna/form";
    }

    @PostMapping("")
    public String createQuestion(String title, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLogin(session)) {
            return "/users/loginForm";
        }
        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        Question question = new Question(sessionUser, title, contents);
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String viewQuestionContents(@PathVariable Long id, Model model) {
        try {
            checkNotFound(id);
            model.addAttribute("question", questionRepository.findById(id).get());
            model.addAttribute("answers", answerRepository.findAll());
            return "/qna/show";
        } catch (NoSuchElementException e) {
            System.out.println("ERROR CODE > " + e.toString());
            return e.getMessage();
        }
    }

    @GetMapping("/{id}/form")
    public String viewUpdatedForm(@PathVariable Long id, Model model, HttpSession session) {
        try {
            model.addAttribute("question", getVerifiedQuestion(id, session));
            return "/qna/updatedForm";
        } catch (NullPointerException | IllegalAccessException | NoSuchElementException e) {
            System.out.println("ERROR CODE > " + e.toString());
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
            System.out.println("ERROR CODE > " + e.toString());
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
            System.out.println("ERROR CODE > " + e.toString());
            return e.getMessage();
        }
    }

    private void checkNotFound(Long id) {
        if (!questionRepository.findById(id).isPresent()) {
            throw new NoSuchElementException("/error/notFound");
        }
    }

    private Question getVerifiedQuestion(Long id, HttpSession session) throws IllegalAccessException {
        checkNotFound(id);
        if (!HttpSessionUtils.isLogin(session)) {
            throw new NullPointerException("/error/unauthorized");
        }
        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(id).get();
        if (!question.isWriterEquals(sessionUser)) {
            throw new IllegalAccessException("/error/unauthorized");
        }
        return question;
    }
}
