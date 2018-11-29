package codesquad.answer;

import codesquad.HttpSessionUtils;
import codesquad.exception.QuestionException;
import codesquad.question.Question;
import codesquad.question.QuestionRepository;
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

        HttpSessionUtils.isLoginUser(session);

        User loginUser = HttpSessionUtils.getUserFormSession(session);
        Question question = questionRepository.findById(questionId).orElse(null);
        Answer answer = new Answer(question,loginUser, contents);
        return answerRepository.save(answer);


        /*//logger.info("answer info");
        HttpSessionUtils.isLoginUser(session);
        Question question = questionRepository.findById(questionId).orElseThrow(QuestionException::new);
        question.addAnswer(new Answer(question, HttpSessionUtils.getUserFormSession(session), contents));

        return questionRepository.save(question);*/
    }
}
