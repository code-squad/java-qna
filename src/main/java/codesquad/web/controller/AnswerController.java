package codesquad.web.controller;

import codesquad.web.domain.model.Answer;
import codesquad.web.domain.model.Question;
import codesquad.web.domain.model.User;
import codesquad.web.domain.repository.AnswerRepository;
import codesquad.web.domain.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

import static codesquad.web.utils.HttpSessionUtils.USER_SESSION_KEY;
import static codesquad.web.utils.HttpSessionUtils.isLoginUser;

@Controller
@RequestMapping("/answers")
public class AnswerController {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("/question/{id}")
    public String createAnswer(@PathVariable("id") Long id, String contents, HttpSession session) {
        if (!isLoginUser(session)) return "user/login";
        Question question = questionRepository.findById(id).get();
        User user = (User)session.getAttribute(USER_SESSION_KEY);
        Answer answer = new Answer(question, user, contents);
        answerRepository.save(answer);
        return "redirect:/questions/" + question.getId();
    }

}
