package codesquad.answer;

import codesquad.qna.Question;
import codesquad.qna.QuestionRepository;
import codesquad.user.User;
import codesquad.utility.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/qna/{questionId}/answers")
public class AnswerController {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping("")
    public String create(@PathVariable long questionId, String contents, HttpSession session){
        if (!HttpSessionUtils.isNullLoginUser(session)) {       // 로그인 안한 상태로 수정을 하려면 로그인을 먼저 해야 한다.
            return "/user/login";
        }

        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(questionId).orElse(null);
        Answer answer = new Answer(loginUser,question,contents);
        answerRepository.save(answer);
        return String.format("redirect:/qna/%d",questionId);
    }


}
