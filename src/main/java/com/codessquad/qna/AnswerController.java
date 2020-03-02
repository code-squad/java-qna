package com.codessquad.qna;

import com.codessquad.qna.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class AnswerController {
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionRepository questionRepository;

    // 답변 처리하는 컨트롤러, 처리 후 show로 redirect
    @PostMapping("/{question.id}/answers")
    public String answer(@PathVariable("question.id") Long questionId,
                         @RequestParam String contents,
                         HttpSession httpSession) {
        User sessionedUser = HttpSessionUtils.getUserFromSession(httpSession);

        if (!HttpSessionUtils.isLoginUser(sessionedUser)) {
            return "redirect:/users/loginForm";
        }
        Question question = questionRepository.findById(questionId).orElseThrow(() ->
                new IllegalStateException("There is no question."));

        Answer answer = new Answer(question, contents, sessionedUser.getName());
        answerRepository.save(answer);
        return "redirect:/questions/{question.id}";
    }

}
