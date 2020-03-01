package com.codessquad.qna.controller;

import com.codessquad.qna.repository.*;
import com.codessquad.qna.util.HttpSessionUtil;
import com.codessquad.qna.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Optional;

@Controller
public class AnswerController {
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    QuestionRepository questionRepository;

    @GetMapping("/questions/{id}/answers")
    public Object showAnswerList(@PathVariable Long id, Model model) {
        ArrayList<Answer> answerList = answerRepository.findByQuestionId(id);
        model.addAttribute("answers", answerList);
        return ResponseEntity.ok(model);
    }

    @PostMapping("/questions/{id}/answers")
    public Object createAnswer(@PathVariable Long id, @RequestBody Answer answer, HttpSession session) {
        if (!HttpSessionUtil.isAuthorizedUser(session)) {
            return PathUtil.UNAUTHORIZED;
        }

        Optional<Question> question = questionRepository.findById(id);
        if (!question.isPresent()) {
            return PathUtil.NOT_FOUND;
        }

        User user = HttpSessionUtil.getUserFromSession(session);
        answer.setWriter(user);
        answer.setQuestion(question.get());
        if (!answer.isCorrectFormat(answer)) {
            return PathUtil.BAD_REQUEST;
        }
        answerRepository.save(answer);
        return "redirect:/questions/" + id;
    }
}
