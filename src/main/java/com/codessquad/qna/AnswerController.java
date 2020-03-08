package com.codessquad.qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;

@Controller
public class AnswerController {
    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    ///댓글 작성 눌렀을 때
    @PostMapping("/questions/{postNumber}/comments")
    public String makeComments(@PathVariable Long postNumber, String contents, HttpSession session) {
        if (!HttpSessionUtil.isLoginUser(session)) {
            return "loginForm";
        }

        User writer = HttpSessionUtil.getUserFromSession(session);
        ///댓글을 작성할 때 이름,내용,질문,질문번호 를 넣어 생성
        Question question = questionRepository.findById(postNumber).orElseThrow(() -> new EntityNotFoundException("/error/notFound"));

        Answer answer = new Answer(writer, question, contents, postNumber);
        answerRepository.save(answer);

        System.out.println("-------------comments created");
        return String.format("redirect:/questions/%d/contents", postNumber);
    }

}
