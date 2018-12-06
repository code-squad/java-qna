package codesquad.answer;

import codesquad.HttpSessionUtils;
import codesquad.question.QnaRepository;
import codesquad.question.Question;
import codesquad.user.User;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

    @Autowired
    private QnaRepository qnaRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping("")
    public String create(@PathVariable long questionId, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/user/login";
        }
        User user = HttpSessionUtils.getUserFromSession(session);
        Question question = qnaRepository.findById(questionId).orElseThrow(IllegalAccessError::new);
        answerRepository.save(new Answer(user, question, contents));
        return String.format("redirect:/qna/%d", questionId);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable long questionId, @PathVariable long id, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/user/login";
        }
        User user = HttpSessionUtils.getUserFromSession(session);
        Answer answerUser = answerRepository.findById(id).get();
        if (!user.equals(answerUser.getWriter())) {
            throw new IllegalStateException("you can't delete other's answers");
        }
        answerRepository.deleteById(id);
        return String.format("redirect:/qna/%d", questionId);
    }

}
