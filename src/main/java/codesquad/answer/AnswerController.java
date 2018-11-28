package codesquad.answer;

import codesquad.exception.Result;
import codesquad.question.Question;
import codesquad.question.QuestionRepository;
import codesquad.user.User;
import codesquad.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping("")
    public String create(@PathVariable long questionId, String contents, Model model, HttpSession session) {
        if (isValid(model, session, null)) return "/user/login";

        User sessionedUser = SessionUtil.getUserFromSession(session);
        Question question = questionRepository.findById(questionId).get();
        Answer answer = Answer.newInstance(sessionedUser, question ,contents);
        answerRepository.save(answer);

        return "redirect:/questions/{questionId}";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable long id, Model model, HttpSession session) {
        Answer answer = answerRepository.findById(id).orElse(null);
        if (isValid(model, session, answer)) return "/user/login";

        answerRepository.delete(answer);
        return "redirect:/questions/{questionId}";
    }

    private boolean isValid(Model model, HttpSession session, Answer answer) {
        Result result = valid(session, answer);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return true;
        }
        return false;
    }

    private Result valid(HttpSession session, Answer answer) {
        if (!SessionUtil.isSessionedUser(session)) {
            return Result.fail("You need login");
        }
        if(answer == null) {
            return Result.success();
        }
        User sessionedUser = SessionUtil.getUserFromSession(session);
        if (!answer.isSameWriter(sessionedUser)) {
            return Result.fail("You can't edit the other user's question");
        }

        return Result.success();
    }

}
