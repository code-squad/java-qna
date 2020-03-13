package com.codessquad.qna.controller;

import com.codessquad.qna.exception.NoSuchElementException;
import com.codessquad.qna.exception.UnauthorizedException;
import com.codessquad.qna.exception.WrongFormatException;
import com.codessquad.qna.repository.*;
import com.codessquad.qna.util.ErrorMessageUtil;
import com.codessquad.qna.util.HttpSessionUtil;
import com.codessquad.qna.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @GetMapping("/form")
    public String showForm(HttpSession session) {
        if (!HttpSessionUtil.isAuthorizedUser(session)) {
            throw new UnauthorizedException(PathUtil.UNAUTHORIZED, ErrorMessageUtil.LOGIN);
        }
        return PathUtil.QUESTION_FORM_TEMPLATE;
    }

    @GetMapping("/{id}")
    public Object showQuestion(@PathVariable Long id, Model model) {
        Question question = questionRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException(PathUtil.NOT_FOUND, ErrorMessageUtil.NOTFOUND_QUESTION));

        model.addAttribute("question", question);
        return PathUtil.QUESTION_DETAIL_TEMPLATE;
    }

    @GetMapping("{id}/editForm")
    public Object showEditPage(@PathVariable Long id, Model model, HttpSession session) {
        Question question = questionRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException(PathUtil.NOT_FOUND, ErrorMessageUtil.NOTFOUND_QUESTION));

        if (!verifyUser(session, question))
            throw new UnauthorizedException(PathUtil.UNAUTHORIZED, ErrorMessageUtil.UNAUTHORIZED);

        model.addAttribute("question", question);
        return PathUtil.QUESTION_EDIT_TEMPLATE;
    }

    @PostMapping
    public String createQuestion(String title, String contents, HttpSession session) {
        User user = HttpSessionUtil.getUserFromSession(session);
        Question question = new Question(title, contents, user);
        if (!question.isCorrectFormat(question)) {
            throw new WrongFormatException(PathUtil.BAD_REQUEST, ErrorMessageUtil.WRONG_FORMAT);
        }
        questionRepository.save(question);
        return PathUtil.HOME;
    }

    @PutMapping("/{id}")
    public Object updateQuestion(@PathVariable Long id, Question updateData, HttpSession session) {
        Question question = questionRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException(PathUtil.NOT_FOUND, ErrorMessageUtil.NOTFOUND_QUESTION));

        if (!verifyUser(session, question))
            throw new UnauthorizedException(PathUtil.UNAUTHORIZED, ErrorMessageUtil.UNAUTHORIZED);

        question.update(updateData);
        questionRepository.save(question);
        return PathUtil.REDIRECT_QUESTION_DETAIL + question.getId();
    }

    @Transactional
    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable Long id, HttpSession session) {
        Question question = questionRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException(PathUtil.NOT_FOUND, ErrorMessageUtil.NOTFOUND_QUESTION));

        if (!verifyUser(session, question))
            throw new UnauthorizedException(PathUtil.UNAUTHORIZED, ErrorMessageUtil.UNAUTHORIZED);

        answerRepository.deleteByQuestion(question);
        questionRepository.delete(id);
        return PathUtil.HOME;
    }

    private boolean verifyUser(HttpSession session, Question question) {
        User user = HttpSessionUtil.getUserFromSession(session);
        return question.isCorrectWriter(user);
    }
}
