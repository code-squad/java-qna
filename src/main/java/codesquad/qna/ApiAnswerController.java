package codesquad.qna;

import codesquad.user.HttpSessionUtils;
import codesquad.user.User;
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
        System.out.println("API 만들기");
        if (!HttpSessionUtils.isLoginUser(session)) {
            return null;
        }

        System.out.println("컨탠츠 : " + contents);
        User loginUser = HttpSessionUtils.getUserFormSession(session);
        Question question = questionRepository.findById(questionId).orElse(null);
        Answer answer = new Answer(loginUser, contents, question);
        System.out.println(question.toString());
        System.out.println(answer.toString());

        return answerRepository.save(answer);
    }
}
