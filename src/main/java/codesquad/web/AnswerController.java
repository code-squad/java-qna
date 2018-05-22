package codesquad.web;

import codesquad.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
    private static final Logger log = LoggerFactory.getLogger(AnswerController.class);
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("")
    public String createAnswer(@PathVariable Long questionId, Answer answer, HttpSession session) {
        log.debug("beforeAnswer : {}", answer);
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/user/login";
        }
        User user = HttpSessionUtils.getSessionedUser(session);
        answer.setWriter(user);

        Question question = questionRepository.findOne(questionId);
        answerRepository.save(answer);
        log.debug("Answer Save Success");
        return String.format("redirect:/questions/%d", questionId);
    }

    @GetMapping("/{id}")
    public String searchAnswer(@PathVariable Long id, HttpSession session, Model model) {
        try {
            Answer answer = answerRepository.findOne(id);
            hasPermission(session, answer);
            model.addAttribute("answer", answer);
            return "/qna/updateAnswerForm";
        } catch (IllegalStateException e) {
            log.debug("error : {}", e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());
            return "/user/login";
        }
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long questionId, @PathVariable Long id, HttpSession session, Model model, String contents) {
        try {
            Answer answer = answerRepository.findOne(id);
            hasPermission(session, answer);
            answer.update(contents);
            answerRepository.save(answer);
            return String.format("redirect:/questions/%d", questionId);
        } catch (IllegalStateException e) {
            log.debug("error : {}", e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());
            return "/user/login";
        }
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session, Model model) {
        try {
            Answer answer = answerRepository.findOne(id);
            hasPermission(session, answer);
            answerRepository.delete(id);
            log.debug("Answer delete");
            return String.format("redirect:/questions/%d", questionId);
        } catch (IllegalStateException e) {
            log.debug("error : {}", e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());
            return "/user/login";
        }
    }

    private void hasPermission(HttpSession session, Answer answer) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            throw new IllegalStateException("You need login");
        }
        User user = HttpSessionUtils.getSessionedUser(session);
        if (!user.isSameWriterOfAnswer(answer)) {
            throw new IllegalStateException("You can't update,delete another user's answer");
        }
    }
}
