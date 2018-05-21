package codesquad.web;

import codesquad.domain.model.Answer;
import codesquad.domain.model.Question;
import codesquad.domain.model.User;
import codesquad.domain.repository.AnswerRepository;
import codesquad.domain.repository.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

import static codesquad.domain.utils.HttpSessionUtils.USER_SESSION_KEY;
import static codesquad.domain.utils.HttpSessionUtils.isLoginUser;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

    private final Logger log = LoggerFactory.getLogger(AnswerController.class);

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("")
    public String createAnswer(@PathVariable("questionId") Long id, String contents, HttpSession session) {
        if (!isLoginUser(session)) return "user/login";
        Question question = questionRepository.findById(id).get();
        User user = (User)session.getAttribute(USER_SESSION_KEY);
        Answer answer = new Answer(question, user, contents);
        answerRepository.save(answer);
        return "redirect:/questions/" + question.getId();
    }

    @DeleteMapping("/{answerId}")
    public String deleteAnswer(@PathVariable("questionId") Long qId, @PathVariable("answerId") Long aId, HttpSession session) {
        log.debug("답변 수정");
        if (!isLoginUser(session)) return "user/login";
        return answerRepository.findById(aId)
                .filter(a -> a.matchWriter((User) session.getAttribute(USER_SESSION_KEY)))
                .map(a -> {
                    answerRepository.deleteById(aId);
                    return "redirect:/questions/" + qId;
                })
                .orElseThrow(() -> new IllegalStateException("cannot change other's answer!!!"));
    }


}
