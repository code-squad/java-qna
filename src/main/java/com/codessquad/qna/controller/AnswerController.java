package com.codessquad.qna.controller;

import com.codessquad.qna.domain.*;
import com.codessquad.qna.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("test-test")
    public String test() {
        return "users/login";
    }

    @PostMapping("")
    public String create(@PathVariable Long questionId, HttpSession session, String contents){
        LOGGER.debug("[page]댓글 작성 요청");

        if(!HttpSessionUtils.isLoginUser(session)) {
            LOGGER.debug("[page]비로그인 상태");
            return "redirect:/users/loginForm";
        }

        Question question = questionRepository.findById(questionId).orElseThrow(() -> new NotFoundException("존재하지 않는 질문입니다."));
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Answer answer = new Answer(loginUser, question, contents);
        answerRepository.save(answer);

        return String.format("redirect:/questions/%d", questionId);
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable Long id, @PathVariable Long questionId, HttpSession session) {
        LOGGER.debug("[page] : {}", "댓글 삭제 요청");

        if(!HttpSessionUtils.isLoginUser(session)) {
            LOGGER.debug("[page] : {}", "비로그인 상태");
            return "redirect:/users/loginForm";
        }

        User user = HttpSessionUtils.getUserFromSession(session);
        Answer answer = answerRepository.findById(id).orElseThrow(() -> new NotFoundException("존재하지 않는 댓글입니다."));
        if(!answer.isSameUser(user)){
            LOGGER.debug("[page] : {}", "글 작성자 아님");
            throw new IllegalStateException("글 작성자 아님");
        }

        answerRepository.deleteById(id);

        return String.format("redirect:/questions/%d", questionId);
    }

}
