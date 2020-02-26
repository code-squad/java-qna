package com.codessquad.qna.controller;

import com.codessquad.qna.repository.Question;
import com.codessquad.qna.repository.QuestionRepository;
import com.codessquad.qna.repository.User;
import com.codessquad.qna.util.HttpSessionUtil;
import com.codessquad.qna.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.Optional;


@Controller
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/form")
    public String showForm(HttpSession session) {
        User user = HttpSessionUtil.getUserFromSession(session);
        if (user == null) {
            return PathUtil.UNAUTHORIZED;
        }
        return PathUtil.QUESTION_FORM_TEMPLATE;
    }

    @GetMapping("/{id}")
    public Object showQuestion(@PathVariable Long id, Model model) {
        Optional<Question> question = questionRepository.findById(id);
        if (question.isPresent()) {
            model.addAttribute("question", question.get());
            return PathUtil.QUESTION_DETAIL_TEMPLATE;
        }
        return PathUtil.NOT_FOUND;
    }

    @GetMapping("{id}/editForm")
    public Object showEditPage(@PathVariable Long id, Model model, HttpSession session) {
        User user = HttpSessionUtil.getUserFromSession(session);
        Optional<Question> question = questionRepository.findById(id);
        if (user == null) {
            return PathUtil.UNAUTHORIZED;
        }
        if (!question.isPresent()) {
            return PathUtil.NOT_FOUND;
        }
        if (!question.get().isCorrectWriter(user)) {
            return PathUtil.UNAUTHORIZED;
        }
        model.addAttribute("question", question.get());
        return PathUtil.QUESTION_EDIT_TEMPLATE;
    }

    @PostMapping
    public String createQuestion(String title, String contents, HttpSession session) {
        if (!HttpSessionUtil.isAuthorizedUser(session))
            return PathUtil.UNAUTHORIZED;

        User user = HttpSessionUtil.getUserFromSession(session);
        Question question = new Question(title, contents, user);

        if(question.isCorrectFormat(question)) {
            questionRepository.save(question);
            return PathUtil.HOME;
        }
        return PathUtil.BAD_REQUEST;
    }

    @PutMapping("/{id}")
    public Object updateQuestion(@PathVariable Long id, Question updateData) {
        Optional<Question> originalQuestion = questionRepository.findById(id);
       if (originalQuestion.isPresent()) {
           return update(originalQuestion.get(), updateData);
       }
       return PathUtil.NOT_FOUND;
    }

    private Object update(Question question, Question updateData) {
        if (question.isCorrectFormat(updateData)){
            question.update(updateData);
            questionRepository.save(question);
            return "redirect:/questions/{id}";
        }
        return PathUtil.BAD_REQUEST;
    }
}
