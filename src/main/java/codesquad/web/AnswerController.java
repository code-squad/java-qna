package codesquad.web;

import codesquad.domain.model.Answer;
import codesquad.domain.model.Question;
import codesquad.domain.model.User;
import codesquad.domain.repository.AnswerRepository;
import codesquad.domain.repository.QuestionRepository;
import codesquad.domain.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

import static codesquad.domain.utils.HttpSessionUtils.USER_SESSION_KEY;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

    private final Logger log = LoggerFactory.getLogger(AnswerController.class);

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("")
    public String createAnswer(@PathVariable("questionId") Long id, String contents, HttpSession session, Model model) {
        Question question = questionRepository.findById(id).get();
        Result result = Result.valid(session, question);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "user/login";
        }
        User user = (User) session.getAttribute(USER_SESSION_KEY);
        Answer answer = new Answer(question, user, contents);
        answerRepository.save(answer);
        return String.format("redirect:/questions/%d", question.getId());
    }

    @DeleteMapping("/{answerId}")
    public String deleteAnswer(@PathVariable("questionId") Long qId, @PathVariable("answerId") Long aId, HttpSession session, Model model) {
        log.debug("답변 삭제");
        Answer answer = answerRepository.findById(aId).get();
        Result result = Result.valid(session, answer);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "user/login";
        }
        answerRepository.deleteById(aId);
        return String.format("redirect:/questions/%d", qId);

    }


}
