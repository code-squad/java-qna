package com.codesquad.qna.web;

import com.codesquad.qna.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{questionId}")
public class AnswerController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping("/answers")
    public String write(@PathVariable Long questionId, Answer answer, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            throw new UserNotPermittedException();
        }

        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        Question selectedQuestion = questionRepository.findById(questionId).orElseThrow(QuestionNotFoundException::new);
        answer.setWriter(sessionedUser);
        answer.setQuestion(selectedQuestion);
        answerRepository.save(answer);
        logger.info("답변 {} 등록에 성공 했습니다.", answer);

        return "redirect:/questions/{questionId}";
    }

    @DeleteMapping("/answers/{id}")
    public String delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            throw new UserNotPermittedException();
        }

        Answer selectedAnswer = answerRepository.findById(id).orElseThrow(QuestionNotFoundException::new);
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);

        if (!selectedAnswer.isSameWriter(sessionedUser)) {
            throw new UserNotPermittedException();
        }

        logger.info("답변 {} 를 삭제 하였습니다.", selectedAnswer);
        answerRepository.delete(selectedAnswer);

        return "redirect:/questions/{questionId}";
    }
}
