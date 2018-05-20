package codesquad.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AnswerController {
    private static final Logger log = LoggerFactory.getLogger(AnswerController.class);
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("/questions/{questionId}/answers")
    public String createAnswer(@PathVariable Long questionId, Answer answer, HttpSession session) {
        log.debug("beforeAnswer : {}", answer);
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/user/login.html";
        }

        User user = HttpSessionUtils.getSessionedUser(session);
        answer.setWriter(user);

        Question question = questionRepository.findOne(questionId);
        question.addAnswers(answer);
        answerRepository.save(answer);
        log.debug("Answer Save Success");
        return String.format("redirect:/questions/%d", questionId);
    }
}
