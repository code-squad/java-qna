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
    public String write(@PathVariable Long questionId, @RequestParam String contents, HttpSession session) {
        User writer = HttpSessionUtils.couldGetValidUserFromSession(session);
        Question selectedQuestion = questionRepository.findById(questionId).orElseThrow(QuestionNotFoundException::new);
        Answer answer = new Answer(writer, selectedQuestion, contents);
        answerRepository.save(answer);
        logger.info("답변 {} 등록에 성공 했습니다.", answer);

        return "redirect:/questions/{questionId}";
    }

    @DeleteMapping("/answers/{id}")
    public String delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        Answer selectedAnswer = answerRepository.findById(id).orElseThrow(QuestionNotFoundException::new);
        User writer = HttpSessionUtils.couldGetValidUserFromSession(session);

        if (!selectedAnswer.isSameWriter(writer)) {
            throw new UserNotPermittedException();
        }

        logger.info("답변 {}를 삭제 하였습니다.", selectedAnswer);
        answerRepository.delete(selectedAnswer);

        return "redirect:/questions/{questionId}";
    }
}
