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
        Answer answer = answerRepository.findOne(id);
        Result result = isValid(session, answer);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "/user/login";
        }
        model.addAttribute("answer", answer);
        return "/qna/updateAnswerForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long questionId, @PathVariable Long id, HttpSession session, Model model, String contents) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            Result result = Result.fail("You need login");
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "/user/login";
        }
        Answer answer = answerRepository.findOne(id);
        User loginedUser = HttpSessionUtils.getSessionedUser(session);
        Result result = answer.update(loginedUser, contents);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "/user/login";
        }
        answerRepository.save(answer);
        return String.format("redirect:/questions/%d", questionId);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session, Model model) {
        Answer answer = answerRepository.findOne(id);
        Result result = isValid(session, answer);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "/user/login";
        }
        answerRepository.delete(id);
        log.debug("Answer delete");
        return String.format("redirect:/questions/%d", questionId);
    }

    private Result isValid(HttpSession session, Answer answer) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return Result.fail("You need login");
        }
        User user = HttpSessionUtils.getSessionedUser(session);
        if (!user.isSameWriterOfAnswer(answer)) {
            return Result.fail("You can't update,delete another user's answer");
        }
        return Result.ok();
    }
}
