package codesquad.answer;

import codesquad.qna.Question;
import codesquad.qna.QuestionRepository;
import codesquad.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {


    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping("")
    public Answer create(@PathVariable Long questionId, String contents, HttpSession session) {
        if (!SessionUtil.isLoginUser(session)) return null;

        Question question = questionRepository.findById(questionId).orElse(null);
        Answer answer = new Answer(question, SessionUtil.getUserFromSesssion(session), contents);

        return answerRepository.save(answer);
    }

}
