package codesquad.answer;

import codesquad.HttpSessionUtils;
import codesquad.question.Question;
import codesquad.question.QuestionRepository;
import codesquad.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        if (!HttpSessionUtils.isLoggedInUser(session)) {
            return null;
        }

        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(questionId).orElse(null);
        Answer answer = new Answer(question, loginUser, contents);
        question.addAnswer();
        return answerRepository.save(answer);
    }
}