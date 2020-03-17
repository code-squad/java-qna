package com.codesquad.qna.web;

import com.codesquad.qna.domain.Question;
import com.codesquad.qna.domain.User;
import com.codesquad.qna.service.AnswerService;
import com.codesquad.qna.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.RollbackException;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolationException;

import static com.codesquad.qna.UrlStrings.REDIRECT_MAIN;
import static com.codesquad.qna.web.HttpSessionUtils.getUserFromSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    @GetMapping("/create")
    public String createForm(HttpSession session) {
        getUserFromSession(session);
        return "qna/form";
    }

    @PostMapping("")
    public String create(Question question, Model model, HttpSession session) {
        User loginUser = getUserFromSession(session);
        Question createdQuestion = new Question(loginUser, question);
        questionService.save(createdQuestion);
        return REDIRECT_MAIN.getUrl();
    }

    @GetMapping("/{id}")
    public String read(@PathVariable Long id, Model model, HttpSession session) {
        Question question = questionService.findById(id);
        model.addAttribute("question", question);

        if (getUserFromSession(session).isIdEquals(question)) {
            model.addAttribute("hasPermissionUser", true);
        }

        model.addAttribute("answers", answerService.findAllByQuestionId(id));
        return "qna/show";
    }

    @GetMapping("/{id}/update")
    public String updateForm(@PathVariable Long id, HttpSession session, Model model) {
        User loginUser = getUserFromSession(session);
        Question updatingQuestion = questionService.findById(id);
        loginUser.hasPermission(updatingQuestion);
        model.addAttribute("updatingQuestion", updatingQuestion);
        return "qna/form";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, HttpSession session, Question updatedQuestion) {
        User loginUser = getUserFromSession(session);
        Question question = questionService.findById(id);
        loginUser.hasPermission(question);
        questionService.update(question, updatedQuestion);
        return "redirect:/questions/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        User loginUser = getUserFromSession(session);
        Question question = questionService.findById(id);
        loginUser.hasPermission(question);
        questionService.delete(question);
        return REDIRECT_MAIN.getUrl();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public String noInputWhenCreate() {
        return "redirect:/questions/create";
    }

    @ExceptionHandler(RollbackException.class)
    public String noInputWhenUpdate() {
        return REDIRECT_MAIN.getUrl();
    }
}
