package com.codessquad.qna;

import com.codesquad.web.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/questions/{id}/update")
    public String updateQuestion(@PathVariable("id") Long id, HttpSession session, Model model) throws IllegalAccessException {
        if (!HttpSessionUtils.isUserLogin(session)) {
            return "redirect:/login";
        }

        Optional optionalQuestion = questionRepository.findById(id);
        if (!optionalQuestion.isPresent()) {
            return "redirect:/";
        }

        Question question = (Question) optionalQuestion.get();
        if (!HttpSessionUtils.getUserFromSession(session).matchName(question.getWriter())) {
            throw new IllegalAccessException("자신이 올린 게시글만 수정할 수 있습니다.");
        }

        model.addAttribute("question", question);
        return "/qna/updateForm";
    }

    @PutMapping("/questions/{id}/update")
    public String putQuestion(@PathVariable("id") Long id, Question updatedQuestion, HttpSession session) throws IllegalAccessException {
        if (!HttpSessionUtils.isUserLogin(session)) {
            return "redirect:/login";
        }

        Optional optionalQuestion = questionRepository.findById(id);

        if (!optionalQuestion.isPresent()) {
            return "redirect:/";
        }

        Question question = (Question) optionalQuestion.get();
        if (!HttpSessionUtils.getUserFromSession(session).matchName(question.getWriter())) {
            throw new IllegalAccessException("자신이 올린 게시글만 수정할 수 있습니다.");
        }

        question.update(updatedQuestion);
        questionRepository.save(question);
        return "redirect:/questions/" + id;
    }

    @DeleteMapping("/questions/{id}/delete")
    public String deleteQuestion(@PathVariable("id") Long id, HttpSession session) throws IllegalAccessException {
        if (!HttpSessionUtils.isUserLogin(session)) {
            return "redirect:/login";
        }

        Optional optionalQuestion = questionRepository.findById(id);
        if (!optionalQuestion.isPresent()) {
            return "redirect:/";
        }

        Question question = (Question) optionalQuestion.get();
        if (!HttpSessionUtils.getUserFromSession(session).matchName(question.getWriter())) {
            throw new IllegalAccessException("자신이 올린 게시글만 삭제할 수 있습니다.");
        }

        questionRepository.delete(question);
        return "redirect:/";
    }
}
