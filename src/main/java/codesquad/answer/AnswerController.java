package codesquad.answer;

import codesquad.question.Question;
import codesquad.question.QuestionRepository;
import codesquad.user.User;
import codesquad.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{id}/answers")
public class AnswerController {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping("")
    public String create(@PathVariable long id, String contents, HttpSession session) {
        if (!SessionUtil.isSessionedUser(session)) return "redirect:/users/login";

        User sessionedUser = SessionUtil.getUserFromSession(session);
        Question question = questionRepository.findById(id).get();
        Answer answer = Answer.newInstance(sessionedUser, question ,contents);

        answerRepository.save(answer);

        return "redirect:/questions/{id}";
    }

}
