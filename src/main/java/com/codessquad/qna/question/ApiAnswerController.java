package com.codessquad.qna.question;

import com.codessquad.qna.constants.CommonConstants;
import com.codessquad.qna.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping("")
    public Answer create(@PathVariable Long questionId, String comment, HttpSession session) {
        User loginUser = (User) session.getAttribute(CommonConstants.SESSION_LOGIN_USER);
        if (loginUser == null) {
            return null;
        }

        Question question = questionRepository.findById(questionId).orElse(null);
        Answer answer = new Answer(loginUser, question, comment);
        return answerRepository.save(answer);
    }
}
