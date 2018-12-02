package codesquad.answer;

import codesquad.exception.Result;
import codesquad.question.Question;
import codesquad.question.QuestionRepository;
import codesquad.user.User;
import codesquad.util.SessionUtil;
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
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @DeleteMapping("{id}")
    public String delete(@PathVariable long id, Model model, HttpSession session) {
        Answer answer = answerRepository.findById(id).orElse(null);
        if (isValid(model, session, answer)) return "/user/login";

        answer.delete();
        answerRepository.save(answer);
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
            return Result.fail("You can't edit the other user's answer");
        }

        return Result.success();
    }

}
