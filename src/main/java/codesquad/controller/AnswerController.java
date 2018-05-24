package codesquad.controller;

import codesquad.domain.answer.Answer;
import codesquad.domain.answer.AnswerRepository;
import codesquad.domain.question.Question;
import codesquad.domain.question.QuestionRepository;
import codesquad.domain.user.User;
import codesquad.util.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
    private static final Logger log = LoggerFactory.getLogger(AnswerController.class);

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
        answer.delete(HttpSessionUtils.getUserFromSession(session));
        answerRepo.save(answer);
        return "redirect:/questions/{questionId}";
    }
}
