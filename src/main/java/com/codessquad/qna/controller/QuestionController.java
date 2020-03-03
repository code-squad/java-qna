package com.codessquad.qna.controller;

import com.codessquad.qna.repository.*;
import com.codessquad.qna.util.HttpSessionUtil;
import com.codessquad.qna.util.PathUtil;
import org.graalvm.compiler.nodes.calc.IntegerDivRemNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.Optional;

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
            return PathUtil.UNAUTHORIZED;
        }
        return PathUtil.QUESTION_FORM_TEMPLATE;
    }

    @GetMapping("/{id}")
    public Object showQuestion(@PathVariable Long id, Model model) {
        Optional<Question> question = questionRepository.findById(id);
        if (!question.isPresent()) {
            return PathUtil.NOT_FOUND;
        }
        model.addAttribute("question", question.get());
        return PathUtil.QUESTION_DETAIL_TEMPLATE;
    }

    @GetMapping("{id}/editForm")
    public Object showEditPage(@PathVariable Long id, Model model, HttpSession session) {
        Optional<Question> question = questionRepository.findById(id);
        Result result = valid(session, question);
        if (!result.isValid()) {
            return result.getResult();
        }
        model.addAttribute("question", question.get());
        return PathUtil.QUESTION_EDIT_TEMPLATE;
    }

    @PostMapping
    public String createQuestion(String title, String contents, HttpSession session) {
        if (!HttpSessionUtil.isAuthorizedUser(session)) {
            return PathUtil.UNAUTHORIZED;
        }
        User user = HttpSessionUtil.getUserFromSession(session);
        Question question = new Question(title, contents, user);
        if (!question.isCorrectFormat(question)) {
            return PathUtil.BAD_REQUEST;
        }
        questionRepository.save(question);
        return PathUtil.HOME;
    }

    @PutMapping("/{id}")
    public Object updateQuestion(@PathVariable Long id, Question updateData, HttpSession session) {
        Optional<Question> question = questionRepository.findById(id);
        User user = HttpSessionUtil.getUserFromSession(session);
        Result result = valid(session, question);
        if (!result.isValid()) {
            return result.getResult();
        }
        return update(question.get(), updateData, user);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public Object deleteQuestion(@PathVariable Long id, HttpSession session) {
        Optional<Question> question = questionRepository.findById(id);
        Result result = valid(session, question);
        if (!result.isValid()) {
            return result.getResult();
        }
        answerRepository.deleteByQuestion(question.get());
        questionRepository.deleteById(id);
        return PathUtil.HOME;
    }

    private Result valid(HttpSession session, Optional<Question> question) {
        if (!question.isPresent()) {
            return Result.fail(PathUtil.NOT_FOUND);
        }

        if (!HttpSessionUtil.isAuthorizedUser(session)) {
            return Result.fail(PathUtil.UNAUTHORIZED);
        }

        User user = HttpSessionUtil.getUserFromSession(session);
        if (!question.get().isCorrectWriter(user)) {
            return Result.fail(PathUtil.UNAUTHORIZED);
        }
        return Result.ok();
    }

    private Object update(Question question, Question updateData, User user) {
        updateData.setWriter(user);
        if (question.isCorrectFormat(updateData)){
            question.update(updateData);
            questionRepository.save(question);
            return PathUtil.REDIRECT_QUESTION_DETAIL + question.getId();
        }
        return PathUtil.BAD_REQUEST;
    }
}
