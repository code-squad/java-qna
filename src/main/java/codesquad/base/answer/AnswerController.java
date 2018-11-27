package codesquad.base.answer;

import codesquad.base.qna.QnaRepository;
import codesquad.base.qna.Question;
import codesquad.base.user.HttpSessionUtils;
import codesquad.base.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
    @Autowired
    private QnaRepository qnaRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping("")
    public String create(@PathVariable Long questionId, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/users/loginForm";
        }

        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Question question = qnaRepository.findById(questionId).orElseGet(null);
        Answer answer = new Answer(loginUser, question, contents);
        answerRepository.save(answer);
        return String.format("redirect:/questions/%d", questionId);
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        System.out.println("ㅇㅈㅇㅈㅇㅈㅇㅈ" + id);
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/users/loginForm";
        }

        answerRepository.delete(answerRepository.findById(id).orElseGet(null));
        return String.format("redirect:/questions/%d", id);
    }

}
