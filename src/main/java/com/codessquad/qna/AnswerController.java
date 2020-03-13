package com.codessquad.qna;

import com.codesquad.web.HttpSessionUtils;
import com.codessquad.qna.dto.AnswerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class AnswerController {
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

        answer.setQuestion(question);
        answer.setWriter(HttpSessionUtils.getUserFromSession(session));
        Answer responseAnswer = answerRepository.save(answer);

        return new AnswerDto(responseAnswer);
    }

    @DeleteMapping("")
    public String delete(@PathVariable("questionId") Long questionId, @PathVariable("id") Long id, HttpSession session) {
        if (!HttpSessionUtils.isUserLogin(session)) {
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

    @PutMapping("")
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
