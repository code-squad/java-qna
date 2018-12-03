package codesquad.api;

import codesquad.question.Answer;
import codesquad.question.AnswerRepository;
import codesquad.question.Question;
import codesquad.question.QuestionRepository;
import codesquad.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping("/questions/{questionId}/answers")
    public Answer create(HttpSession session, @PathVariable Long questionId, String contents) {
        User loginUser = (User) session.getAttribute(User.SESSION_NAME);
        Optional<Question> maybeQuestion = questionRepository.findById(questionId);
        if (loginUser != null && maybeQuestion.isPresent()) {
            Answer answer = new Answer(loginUser, maybeQuestion.get(), contents);
            return answerRepository.save(answer);
        }
        throw new RuntimeException();
    }

    @DeleteMapping("/questions/{questionId}/answers/{answerId}")
    public String deleteAnswer(HttpSession session, @PathVariable long questionId, @PathVariable long answerId) {
        Optional<Answer> maybeAnswer = answerRepository.findById(answerId);
        User user = (User) session.getAttribute(User.SESSION_NAME);
        if (maybeAnswer.isPresent() && maybeAnswer.get().isSameWriter(user)) {
            answerRepository.deleteById(answerId);
            return "{\"valid\":true}";
        }
        return "{\"valid\":false}";
    }

}
