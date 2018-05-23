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
        User user = HttpSessionUtils.getUserFromSession(session).get();
        Question question = questionRepo.findById(questionId).get();
        Answer answer = Answer.builder().user(user).question(question).contents(contents).build();
        answerRepo.save(answer);
        return "redirect:/questions/{questionId}";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id) {

        return null;
    }

    @PutMapping("/{id}")
    public String update(@PathVariable("id") Long id) {

        return null;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id, HttpSession session) {
        User sessionUser = HttpSessionUtils.getUserFromSession(session).get();
        Answer answer = answerRepo.findById(id).get();
        answer.delete(sessionUser);
        answerRepo.save(answer);
        return "redirect:/questions/{questionId}";
    }
}
