package com.codesquad.qna.web;

import com.codesquad.qna.domain.AnswerRepository;
import com.codesquad.qna.domain.Question;
import com.codesquad.qna.domain.QuestionRepository;
import com.codesquad.qna.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;

import static com.codesquad.qna.web.HttpSessionUtils.getUserFromSession;
import static com.codesquad.qna.web.HttpSessionUtils.isLoginUser;
import static com.codesquad.qna.web.UserController.REDIRECT_LOGIN_FORM;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @GetMapping("/createForm")
    public String createForm(HttpSession session) {
        if (!isLoginUser(session)) {
            return REDIRECT_LOGIN_FORM;
        }

        return "qna/form";
    }

    @PostMapping("/create")
    public String create(Question question, Model model, HttpSession session) {
        if (!isLoginUser(session)) {
            return REDIRECT_LOGIN_FORM;
        }

        User sessionedUser = getUserFromSession(session);
        Question createdQuestion = new Question(sessionedUser, question);
        questionRepository.save(createdQuestion);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String read(@PathVariable Long id, Model model, HttpSession session) {
        Question question = findQuestion(id);
        model.addAttribute("question", question);

        if (isLoginUser(session) && getUserFromSession(session).hasPermission(id)) {
            model.addAttribute("hasPermissionUser", true);
        }

        model.addAttribute("answers", answerRepository.findAll());
        return "qna/show";
    }

    @GetMapping("/{id}/updateForm")
    public String updateForm(@PathVariable Long id, HttpSession session, Model model) {
        if (!isLoginUser(session)) {
            return REDIRECT_LOGIN_FORM;
        }

        User sessionedUser = getUserFromSession(session);
        Question updatingQuestion = findQuestion(id);

        sessionedUser.hasPermission(updatingQuestion);
        model.addAttribute("updatingQuestion", updatingQuestion);
        return "qna/form";
    }

    @PutMapping("/{id}/update")
    public String update(@PathVariable Long id, HttpSession session, Question updatedQuestion) {
        if (!isLoginUser(session)) {
            return REDIRECT_LOGIN_FORM;
        }

        User sessionedUser = getUserFromSession(session);
        Question question = findQuestion(id);

        sessionedUser.hasPermission(question);
        question.update(updatedQuestion);
        questionRepository.save(question);
        return "redirect:/questions/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        if (!isLoginUser(session)) {
            return REDIRECT_LOGIN_FORM;
        }

        User sessionedUser = getUserFromSession(session);
        Question question = findQuestion(id);

        sessionedUser.hasPermission(question);
        questionRepository.delete(question);
        return "redirect:/";
    }

    private Question findQuestion(Long id) {
        return questionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
