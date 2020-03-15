package com.codesquad.qna.web;

import com.codesquad.qna.global.error.exception.DataNotFoundException;
import com.codesquad.qna.global.error.exception.ErrorCode;
import com.codesquad.qna.global.error.exception.RequestNotAllowedException;
import com.codesquad.qna.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {

    private static final Logger log = LoggerFactory.getLogger(ApiAnswerController.class);
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("")
    public Answer create(@PathVariable Long questionId, String contents, @RequestAttribute User sessionedUser) {
        Question answeredQuestion = findQuestion(questionId);
        Answer newAnswer = new Answer(answeredQuestion, sessionedUser, contents);
        // @Formula로 가져온 값은 새로운 답변 추가가 반영되기 전이니까 임시 방편으로 여기서 추가해준다.
        // deleteAnswer()는 필요 없다.
        answeredQuestion.addAnswer();
        log.debug("count of Answers : {}", answeredQuestion.getCountOfAnswers());
        return answerRepository.save(newAnswer);
    }

    @DeleteMapping("/{answerId}")
    public Result delete(@PathVariable Long questionId, @PathVariable Long answerId, @RequestAttribute User sessionedUser) {
        Answer answer = getMatchedAnswer(answerId, sessionedUser);
        answer.setDeleted(true);
        answerRepository.save(answer);
        return Result.ok();
    }

    @GetMapping("/{answerId}/form")
    public Answer updateForm(@PathVariable Long questionId, @PathVariable Long answerId, @RequestAttribute User sessionedUser, HttpServletResponse response) {
        Answer answer = getMatchedAnswer(answerId, sessionedUser);
        return answer;
    }

    private Question findQuestion(Long questionId) {
        return questionRepository.findById(questionId).orElseThrow(() -> new DataNotFoundException(ErrorCode.DATA_NOT_FOUND));
    }

    private Answer findAnswer(Long answerId) {
        return answerRepository.findById(answerId).orElseThrow(() -> new DataNotFoundException(ErrorCode.DATA_NOT_FOUND));
    }

    private Answer getMatchedAnswer(Long answerId, User sessionedUser) {
        Answer answer = findAnswer(answerId);
        if (!answer.matchWriter(sessionedUser))
            throw new RequestNotAllowedException(ErrorCode.FORBIDDEN);
        return answer;
    }
}
