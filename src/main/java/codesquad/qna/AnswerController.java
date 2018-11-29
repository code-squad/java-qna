package codesquad.qna;

import codesquad.user.HttpSessionUtils;
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
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("")
    public String create(@PathVariable Long questionId, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "user/login";
        }

        User loginUser = HttpSessionUtils.getUserFormSession(session);
        Question question = questionRepository.findById(questionId).orElse(null);
        Answer answer = new Answer(loginUser, contents, question);

        answerRepository.save(answer);
        return String.format("redirect:/questions/%d", questionId);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session, Model model) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "user/login";
        }

        User loginUser = HttpSessionUtils.getUserFormSession(session);
        Answer answer = answerRepository.findById(id).orElse(null);
        if (answer.delete(loginUser)) {
            answerRepository.save(answer);
            return String.format("redirect:/questions/%d", questionId);
        }

        model.addAttribute("errorMessage", Result.failed("오류 !! 댓글 작성자만 삭제할 수 있습니다.").getErrorMessage());
        return "qna/delete_failed";
    }
}
