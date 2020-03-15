package com.codessquad.qna.controller;

import com.codessquad.qna.exception.NoSuchElementException;
import com.codessquad.qna.exception.UnauthorizedException;
import com.codessquad.qna.repository.*;
import com.codessquad.qna.util.ErrorMessageUtil;
import com.codessquad.qna.util.HttpSessionUtil;
import com.codessquad.qna.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    QuestionRepository questionRepository;

    @GetMapping
    public Object showAnswerList(@PathVariable Long questionId, Model model) {
        List<Answer> answerList = answerRepository.findByQuestionIdAndDeletedFalse(questionId);
        model.addAttribute("answers", answerList);
        return ResponseEntity.ok(model);
    }

    @GetMapping("/{answerId}/editForm")
    public String showEditForm(@PathVariable("questionId") Long questionId, @PathVariable("answerId") Long answerId, HttpSession session, Model model) {
        User user = HttpSessionUtil.getUserFromSession(session);
        Question question = findQuestion(questionId);
        Answer answer = findAnswer(answerId);

        if (!answer.isCorrectWriter(user))
           throw new UnauthorizedException(PathUtil.UNAUTHORIZED, ErrorMessageUtil.UNAUTHORIZED);

        model.addAttribute("answer", answer);
        model.addAttribute("question", question);
        return PathUtil.ANSWER_EDIT_TEMPLATE;
    }

    @PutMapping("/{answerId}")
    public String updateAnswer(@PathVariable("questionId") Long questionId, @PathVariable("answerId") Long answerId, String contents ,HttpSession session) {
        User user = HttpSessionUtil.getUserFromSession(session);
        Question question = findQuestion(questionId);
        Answer answer = findAnswer(answerId);

        if (!answer.isCorrectWriter(user)) {
            throw new UnauthorizedException(PathUtil.UNAUTHORIZED, ErrorMessageUtil.UNAUTHORIZED);
        }

        Answer updateData = new Answer(user, question, contents);
        answer.update(updateData);
        answerRepository.save(answer);
        return PathUtil.REDIRECT_QUESTION_DETAIL + questionId;
    }


    private Question findQuestion(Long id) {
        return questionRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException(PathUtil.NOT_FOUND, ErrorMessageUtil.NOTFOUND_QUESTION));
    }

    private Answer findAnswer(Long id) {
        return answerRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException(PathUtil.NOT_FOUND, ErrorMessageUtil.NOTFOUND_ANSWER));
    }
}
