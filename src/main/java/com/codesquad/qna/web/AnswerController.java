package com.codesquad.qna.web;

import com.codesquad.qna.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Controller
public class AnswerController {
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("/answers/{questionId}")
    public String create(@PathVariable Long questionId, String contents, HttpSession session) {
        // 답변 폼 자체가 로그인되지 않으면 보이지 않으므로 로그인 여부는 검사안함
        // Url을 직접 변경하더라도 Post 방식이라 접근이 안됨
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        Question answeredQuestion = findQuestion(questionId);
        Answer newAnswer = new Answer(answeredQuestion, sessionedUser, contents);
        answerRepository.save(newAnswer);
        return "redirect:/questions/" + questionId;
    }

    private Question findQuestion(Long questionId) {
        return questionRepository.findById(questionId).orElseThrow(IllegalArgumentException::new);
    }
}
