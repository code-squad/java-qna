package com.codessquad.qna.controller;

import com.codesquad.web.HttpSessionUtils;
import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.AnswerRepository;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.QuestionRepository;
import com.codessquad.qna.dto.AnswerDto;
import com.codessquad.qna.dto.Result;
import com.codessquad.qna.exception.ProductNotfoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {
    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    QuestionRepository questionRepository;

    @PostMapping("")
    public AnswerDto create(@PathVariable("questionId") Long questionId, HttpSession session, Answer answer) {
        if (!HttpSessionUtils.isUserLogin(session)) {
            return null;
        }

        Optional<Question> optionalQuestion = questionRepository.findActiveQuestionById(questionId);

        optionalQuestion.orElseThrow(ProductNotfoundException::new);

        Question question = optionalQuestion.get();
        question.addAnswer();
        answer.setQuestion(question);
        answer.setWriter(HttpSessionUtils.getUserFromSession(session));
        Answer responseAnswer = answerRepository.save(answer);

        return new AnswerDto(responseAnswer);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("questionId") Long questionId, @PathVariable("id") Long id, HttpSession session) {
        if (!HttpSessionUtils.isUserLogin(session)) {
            return Result.fail("로그인을 해야 합니다.");
        }

        Optional<Answer> optionalAnswer = answerRepository.findActiveAnswerById(id);
        if (!optionalAnswer.isPresent()) {
            return Result.fail("삭제할 글이 없습니다.");
        }

        Answer answer = optionalAnswer.get();
        if (!answer.getWriter().equals(HttpSessionUtils.getUserFromSession(session))) {
            return Result.fail("자신이 작성한 글만 삭제할 수 있습니다.");
        }

        answer.delete();
        questionRepository.findById(questionId).ifPresent(Question::deleteAnswer);
        answerRepository.save(answer);

        return Result.ok();
    }

    @PutMapping("/{id}")
    public AnswerDto update(@PathVariable("questionId") Long questionId, @PathVariable("id") Long id, HttpSession session, Answer updatedAnswer) {
        if (!HttpSessionUtils.isUserLogin(session)) {
            return null;
        }

        Optional<Answer> optionalAnswer = answerRepository.findActiveAnswerById(id);
        if (!optionalAnswer.isPresent()) {
            return null;
        }

        Answer answer = optionalAnswer.get();
        if (!answer.getWriter().equals(HttpSessionUtils.getUserFromSession(session))) {
            return null;
        }
        answer.update(updatedAnswer);
        answerRepository.save(answer);

        return new AnswerDto(answer);
    }
}
