package codesquad.answer;

import codesquad.question.Question;
import codesquad.user.User;
import codesquad.utils.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
//        return "/qna/show";
//        return "redirect:/question/{pId}";
        return String.format("redirect:/question/%d", questionPId);
    }

    @GetMapping("/{pId}/update")
    public String update(@PathVariable long pId, HttpSession session) {
        Answer answer = answerRepository.findById(pId).get();
        if (!HttpSessionUtils.isValid(session, answer)) {
            throw new IllegalArgumentException("에러!");
        }
        return "";
    }

    @DeleteMapping("/{answerPId}/delete")
    public String delete(@PathVariable long answerPId, @PathVariable long questionPId) {
        answerRepository.delete(answerRepository.findById(answerPId).get());
        return String.format("redirect:/question/%d", questionPId);
    }
}
