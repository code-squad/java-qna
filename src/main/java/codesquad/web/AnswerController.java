package codesquad.web;

import codesquad.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

// answer는 question에 종속적이다
@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping
    public String create(@PathVariable Long questionId, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }

        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findOne(questionId);
        Answer answer = new Answer(loginUser, question, contents);
        answerRepository.save(answer);

        return "redirect:/questions/{questionId}";
    }
}
