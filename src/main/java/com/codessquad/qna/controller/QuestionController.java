package com.codessquad.qna.controller;

import com.codessquad.qna.exception.CustomNoSuchElementException;
import com.codessquad.qna.exception.UnauthorizedException;
import com.codessquad.qna.exception.WrongFormatException;
import com.codessquad.qna.repository.*;
import com.codessquad.qna.util.ErrorMessages;
import com.codessquad.qna.util.HttpSessionUtil;
import com.codessquad.qna.util.Paths;
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
            throw new UnauthorizedException(Paths.UNAUTHORIZED, ErrorMessages.LOGIN);
        }
        return Paths.QUESTION_FORM_TEMPLATE;
    }

    @GetMapping("/{id}")
    public Object showQuestion(@PathVariable Long id, Model model) {
        Question question = questionRepository.findById(id).orElseThrow(() ->
                new CustomNoSuchElementException(Paths.NOT_FOUND, ErrorMessages.NOTFOUND_QUESTION));

        model.addAttribute("question", question);
        return Paths.QUESTION_DETAIL_TEMPLATE;
    }

    @GetMapping("{id}/editForm")
    public Object showEditPage(@PathVariable Long id, Model model, HttpSession session) {
        User user = HttpSessionUtil.getUserFromSession(session);
        Question question = questionRepository.findById(id).orElseThrow(() ->
                new CustomNoSuchElementException(Paths.NOT_FOUND, ErrorMessages.NOTFOUND_QUESTION));

        if (!question.isCorrectWriter(user))
            throw new UnauthorizedException(Paths.UNAUTHORIZED, ErrorMessages.UNAUTHORIZED);

        model.addAttribute("question", question);
        return Paths.QUESTION_EDIT_TEMPLATE;
    }

    @PostMapping
    public String createQuestion(String title, String contents, HttpSession session) {
        User user = HttpSessionUtil.getUserFromSession(session);
        Question question = new Question(title, contents, user);

        if (!question.isCorrectFormat(question)) {
            throw new WrongFormatException(Paths.BAD_REQUEST, ErrorMessages.WRONG_FORMAT);
        }

        questionRepository.save(question);
        return Paths.HOME;
    }

    @PutMapping("/{id}")
    public Object updateQuestion(@PathVariable Long id, Question updateData, HttpSession session) {
        User user = HttpSessionUtil.getUserFromSession(session);
        Question question = questionRepository.findById(id).orElseThrow(() ->
                new CustomNoSuchElementException(Paths.NOT_FOUND, ErrorMessages.NOTFOUND_QUESTION));

        if (!question.isCorrectWriter(user))
            throw new UnauthorizedException(Paths.UNAUTHORIZED, ErrorMessages.UNAUTHORIZED);

        question.update(updateData);
        questionRepository.save(question);
        return Paths.REDIRECT_QUESTION_DETAIL + question.getId();
    }

    @Transactional
    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable Long id, HttpSession session) {
        User user = HttpSessionUtil.getUserFromSession(session);
        Question question = questionRepository.findById(id).orElseThrow(() ->
                new CustomNoSuchElementException(Paths.NOT_FOUND, ErrorMessages.NOTFOUND_QUESTION));

        if (!question.isCorrectWriter(user))
            throw new UnauthorizedException(Paths.UNAUTHORIZED, ErrorMessages.UNAUTHORIZED);

        answerRepository.deleteByQuestion(question);
        questionRepository.delete(id);
        return Paths.HOME;
    }
}
