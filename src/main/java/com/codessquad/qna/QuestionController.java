package com.codessquad.qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpSession;

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
        if(!(isIdPresent(id))) {
            return "redirect:/";
        }
        model.addAttribute("question", questionRepository.findById(id).get());
        return "/qna/show";
    }

    @GetMapping("/questions/{id}/form")
    public String viewUpdatedForm(@PathVariable Long id, Model model, HttpSession session) {
        model.addAttribute("question", questionRepository.findById(id).get());
        return "/qna/updatedForm";
    }

    @PutMapping("/questions/{id}")
    public String updateQuestion(@PathVariable Long id, String title, String contents) {
        Question question = questionRepository.findById(id).get();
        question.update(title, contents);
        questionRepository.save(question);
        return "redirect:/questions/" + id;
    }

//    private User getSessionUser(User writer, HttpSession session) throws IllegalAccessException {
//        if (!HttpSessionUtils.isLogin(session)) {
//            throw new NullPointerException();
//        }
//        User sessionUser = HttpSessionUtils.getUserFromSession(session);
//        if (!sessionUser.isIdEquals(writer.getId())) {
//            throw new IllegalAccessException();
//        }
//        return sessionUser;
//    }

    private Boolean isIdPresent(Long id) {
        return questionRepository.findById(id).isPresent();
    }
}
