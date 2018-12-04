package codesquad.answer;

import codesquad.HttpSessionUtil;
import codesquad.Result;
import codesquad.qna.Question;
import codesquad.qna.QuestionRepository;
import codesquad.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping("")
    public String create(@PathVariable Long questionId, String contents, HttpSession session) {
        if (!HttpSessionUtil.isLoginUser(session)) {
            return "/user/login";
        }
        User loginUser = HttpSessionUtil.getUserFromSession(session);
        Question question = questionRepository.findById(questionId).orElseThrow(NullPointerException::new);
        Answer answer = new Answer(loginUser, question, contents);
        answerRepository.save(answer);
        return String.format("redirect:/questions/%d", questionId);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session, Model model) {
        Answer answer = answerRepository.findById(id).orElseThrow(NullPointerException::new);
        Result result = valid(session, answer);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "/user/login_failed";
        }
        answer.delete();
        answerRepository.save(answer);
        return String.format("redirect:/questions/%d", questionId);
    }

    private Result valid(HttpSession session, Answer answer) {
        if (!HttpSessionUtil.isLoginUser(session)) {
            return Result.fail("로그인이 필요합니다.");
        }
        User loginUser = HttpSessionUtil.getUserFromSession(session);
        if (!answer.isSameWriter(loginUser)) {
            return Result.fail("자신이 쓴 댓글만 수정, 삭제가 가능합니다.");
        }
        return Result.ok();
    }
}
