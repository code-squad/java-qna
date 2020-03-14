package com.codessquad.qna.answer;

import com.codessquad.qna.question.Question;
import com.codessquad.qna.question.QuestionRepository;
import com.codessquad.qna.user.User;
import com.codessquad.qna.utils.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
    private Logger log = LoggerFactory.getLogger(AnswerController.class);

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping("")
    public String createAnswer(@PathVariable Long questionId, String contents, HttpSession session) {
        if (HttpSessionUtils.isNoneExistentUser(session)) {
            return "/users/loginForm";
        }

        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(questionId).orElseThrow(IllegalStateException::new);
        Answer answer = new Answer(loginUser, question, contents);
        log.info("answer confirm");
        answerRepository.save(answer);
        log.info("answer save: '{}'",answer);
        return String.format("redirect:/questions/%d", questionId);
    }
}
