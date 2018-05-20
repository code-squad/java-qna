package codesquad.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/questions/{questionId}/answers/{id}")
    public String searchAnswer(@PathVariable Long questionId, @PathVariable Long id, HttpSession session, Model model) {
        if(!HttpSessionUtils.isLoginUser(session)){
            return "redirect:/user/login.html";
        }

        Answer answer = answerRepository.findOne(id);
        User user = HttpSessionUtils.getSessionedUser(session);
        if (!user.isSameWriterOfAnswer(answer)){
            throw new IllegalStateException("You can't update another user's answer");
        }

        model.addAttribute("answer", answer);
        return "/qna/updateAnswerForm";
    }

    @PutMapping("/answers/{id}")
    public String update(@PathVariable Long id, String contents){
        Answer answer = answerRepository.findOne(id);
        answer.update(contents);
        answerRepository.save(answer);
        return String.format("redirect:/questions/%d", answer.getIdOfQuestion());
    }

    @DeleteMapping("/questions/{questionId}/answers/{id}")
    public String delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session){
        if(!HttpSessionUtils.isLoginUser(session)){
            return "redirect:/user/login.html";
        }

        Answer answer = answerRepository.findOne(id);
        User user = HttpSessionUtils.getSessionedUser(session);
        if (!user.isSameWriterOfAnswer(answer)){
            throw new IllegalStateException("You can't delete another user's answer");
        }

        answerRepository.delete(id);
        log.debug("Answer delete");
        return String.format("redirect:/questions/%d", questionId);
    }
}
