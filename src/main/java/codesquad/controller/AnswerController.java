package codesquad.controller;

import codesquad.domain.answer.Answer;
import codesquad.domain.answer.AnswerRepository;
import codesquad.domain.exception.UnAuthorizedException;
import codesquad.domain.question.QuestionRepository;
import codesquad.domain.result.Result;
import codesquad.util.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

    @Autowired
    private AnswerRepository answerRepo;

    @Autowired
    private QuestionRepository questionRepo;

    @PostMapping
    public String create(@PathVariable("questionId") Long questionId, String contents, HttpSession session) {
        Answer answer = Answer.builder().user(HttpSessionUtils.getUserFromSession(session).get()).question(questionRepo.findById(questionId).get()).contents(contents).build();
        answerRepo.save(answer);
        return "redirect:/questions/{questionId}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id, HttpSession session) {
        Answer answer = answerRepo.findById(id).get();
        Result result = answer.delete(HttpSessionUtils.getUserFromSession(session));
        if (!result.isValid()) {
            throw new UnAuthorizedException("answer.user.mismatch.request.user");
        }
        answerRepo.save(answer);
        return "redirect:/questions/{questionId}";
    }
}
