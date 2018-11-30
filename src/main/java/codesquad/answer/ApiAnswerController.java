package codesquad.answer;

import codesquad.HttpSessionUtils;
import codesquad.question.QnaRepository;
import codesquad.question.Question;
import codesquad.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/questions/{id}/answers")
public class ApiAnswerController {

    @Autowired
    private QnaRepository qnaRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping("")
    public Answer create(@PathVariable long id , HttpSession session, String contents) {
        User user = HttpSessionUtils.getUserFromSession(session);
        Question question = qnaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("no!"));
        return answerRepository.save(new Answer(user, question, contents));
    }
}
