package codesquad.controller;

import codesquad.domain.answer.Answer;
import codesquad.domain.answer.AnswerRepository;
import codesquad.domain.question.QuestionRepository;
import codesquad.util.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {
    private static final Logger log = LoggerFactory.getLogger(AnswerController.class);

    @Autowired
    private AnswerRepository answerRepo;

    @Autowired
    private QuestionRepository questionRepo;

    @PostMapping
    public Answer create(@PathVariable("questionId") Long questionId, String contents, HttpSession session) {
        log.info("answer contents : {}", contents);
        Answer answer = Answer.builder().user(HttpSessionUtils.getUserFromSession(session).get()).question(questionRepo.findById(questionId).get()).contents(contents).build();
        return answerRepo.save(answer);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id, HttpSession session) {
        //아직 구현중
        Answer answer = answerRepo.findById(id).get();
        answer.delete(HttpSessionUtils.getUserFromSession(session));
        answerRepo.save(answer);
        return "delete!";
    }
}
