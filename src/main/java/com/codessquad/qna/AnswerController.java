package com.codessquad.qna;

import com.codesquad.web.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class AnswerController {
    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    QuestionRepository questionRepository;

    @PostMapping("/questions/{questionId}/answers")
    public String create(@PathVariable("questionId") Long questionId, HttpSession session, Answer answer) {
        if (!HttpSessionUtils.isUserLogin(session)) {
            return "redirect:/login";
        }

        Optional<Question> optionalQuestion = questionRepository.findActiveQuestionById(questionId);

        optionalQuestion.orElseThrow(ProductNotfoundException::new);

        optionalQuestion.ifPresent(question -> {
            answer.setQuestion(question);
            answer.setWriter(HttpSessionUtils.getUserFromSession(session));
            answerRepository.save(answer);
        });

        return "redirect:/questions/" + questionId;
    }

    @DeleteMapping("questions/{questionId}/answers/{id}")
    public String delete(@PathVariable("questionId") Long questionId, @PathVariable("id") Long id, HttpSession session) {
        if (!HttpSessionUtils.isUserLogin(session)) {
            return "redirect:/login";
        }

        Optional<Answer> optionalAnswer = answerRepository.findActiveAnswerById(id);
        if (!optionalAnswer.isPresent()) {
            throw new ProductNotfoundException();
        }

        Answer answer = optionalAnswer.get();
        if (!answer.getWriter().equals(HttpSessionUtils.getUserFromSession(session))) {
            throw new UnauthorizedException();
        }

        answer.delete();
        answerRepository.save(answer);

        return "redirect:/questions/" + questionId;
    }

    @PutMapping("/questions/{questionId}/answers/{id}")
    public String update(@PathVariable("questionId") Long questionId, @PathVariable("id") Long id, HttpSession session, Answer updatedAnswer) {
        if (!HttpSessionUtils.isUserLogin(session)) {
            return "redirect:/login";
        }

        Optional<Answer> optionalAnswer = answerRepository.findActiveAnswerById(id);
        if (!optionalAnswer.isPresent()) {
            throw new ProductNotfoundException();
        }

        Answer answer = optionalAnswer.get();
        if (!answer.getWriter().equals(HttpSessionUtils.getUserFromSession(session))) {
            throw new UnauthorizedException();
        }
        answer.update(updatedAnswer);
        answerRepository.save(answer);

        return "redirect:/questions/" + questionId;
    }
}
