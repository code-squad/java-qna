package com.codessquad.qna.controller;

import com.codessquad.qna.domain.*;
import com.codessquad.qna.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("")
    public Answer create(@PathVariable Long questionId, HttpSession session, String contents){
        LOGGER.debug("[page]댓글 작성 요청");

        if(!HttpSessionUtils.isLoginUser(session)) {
            LOGGER.debug("[page]비로그인 상태");
            throw new IllegalStateException("권한이 없습니다.");
        }

        Question question = questionRepository.findById(questionId).orElseThrow(() -> new NotFoundException("존재하지 않는 질문입니다."));
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Answer answer = new Answer(loginUser, question, contents);
        //어떻게 업데이트 되는 건지
        question.addCount();
        return answerRepository.save(answer);

    }

    @DeleteMapping("{id}")
    public Result delete(@PathVariable Long id, @PathVariable Long questionId, HttpSession session) {
        LOGGER.debug("[page] : {}", "댓글 삭제 요청");

        if(!HttpSessionUtils.isLoginUser(session)) {
            LOGGER.debug("[page] : {}", "비로그인 상태");
            return Result.fail("login first");
        }

        User user = HttpSessionUtils.getUserFromSession(session);
        Answer answer = answerRepository.findById(id).orElseThrow(() -> new NotFoundException("존재하지 않는 댓글입니다."));
        if(!answer.isSameUser(user)){
            LOGGER.debug("[page] : {}", "글 작성자 아님");
            return Result.fail("zz");
        }

        answer.delete();
        answerRepository.save(answer);

        Question question = questionRepository.findById(questionId).get();
        question.deleteCount();
        questionRepository.save(question);

        return Result.ok();
    }

}
