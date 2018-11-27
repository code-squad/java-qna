package codesquad.answer;

import codesquad.question.Question;
import codesquad.user.User;
import codesquad.utils.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/question/{questionPId}/answer")
public class AnswerController {

    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping("")
    public String create(@PathVariable long questionPId, Answer answer, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/user/login";
        }
        answerRepository.save(answer);
        return String.format("redirect:/question/%d", questionPId);
    }

    @GetMapping("/{answerPId}")
    public String update(@PathVariable long answerPId, @PathVariable long questionPId, HttpSession session) {
        Answer answer = answerRepository.findById(answerPId).get();
        if (!HttpSessionUtils.isValid(session, answer)) {
            throw new IllegalArgumentException("에러!");
        }
        return String.format("redirect:/question/%d", questionPId);
    }

    @DeleteMapping("/{answerPId}")
    public String delete(@PathVariable long answerPId, @PathVariable long questionPId, HttpSession session) {
        Answer answer = answerRepository.findById(answerPId).get();
        if (!HttpSessionUtils.isValid(session, answer)) {
            return String.format("redirect:/question/%d", questionPId);
        }
        answerRepository.delete(answerRepository.findById(answerPId).get());
        return String.format("redirect:/question/%d", questionPId);
    }
}
