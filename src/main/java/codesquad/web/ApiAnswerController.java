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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static codesquad.domain.utils.HttpSessionUtils.USER_SESSION_KEY;
import static codesquad.domain.utils.HttpSessionUtils.getUserFromSession;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {

    private final Logger log = LoggerFactory.getLogger(ApiAnswerController.class);

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("")
    public Answer createAnswer(@PathVariable("questionId") Long id, String contents, HttpSession session, Model model) {
        log.debug("contents : {}", contents);
        Question question = questionRepository.findById(id).get();
        Result result = Result.valid(session);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return null;
        }
        User user = (User) session.getAttribute(USER_SESSION_KEY);
        Answer answer = new Answer(question, user, contents);
        question.addAnswer();
        return answerRepository.save(answer);
    }

    @DeleteMapping("/{answerId}")
    public Result deleteAnswer(@PathVariable("questionId") Long qId, @PathVariable("answerId") Long aId, HttpSession session, Model model) {
        log.debug("답변 삭제");
        Answer answer = answerRepository.findById(aId).get();
        Result result = Result.valid(session, answer, a -> a.matchWriter(getUserFromSession(session)));
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return result;
        }
        answerRepository.deleteById(aId);
        Question question = questionRepository.findById(qId).get();
        question.deleteAnswer();
        questionRepository.save(question);
        return result;
    }

    @GetMapping("/{answerId}/form")
    public Result checkPossibilityOfmodifyAnswer(@PathVariable("questionId") Long qId, @PathVariable("answerId") Long aId, HttpSession session, Model model) {
        log.debug("답변 삭제");
        Answer answer = answerRepository.findById(aId).get();
        Result result = Result.valid(session, answer, a -> a.matchWriter(getUserFromSession(session)));
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return null;
        }
        return result;
    }

    @PutMapping("/{answerId}/formm")
    public Answer modifyAnswer(@PathVariable("questionId") Long qId, @PathVariable("answerId") Long aId, String contents, HttpSession session, Model model) {
        log.debug("답변 삭제");
        Answer answer = answerRepository.findById(aId).get();
        Result result = Result.valid(session, answer, a -> a.matchWriter(getUserFromSession(session)));
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return null;
        }
        answer.update(contents);
        return answerRepository.save(answer);
    }

}
