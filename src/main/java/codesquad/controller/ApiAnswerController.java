package codesquad.controller;

import codesquad.domain.answer.Answer;
import codesquad.domain.answer.AnswerRepository;
import codesquad.domain.question.Question;
import codesquad.domain.question.QuestionRepository;
import codesquad.domain.result.Result;
import codesquad.util.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {
    private static final Logger log = LoggerFactory.getLogger(ApiAnswerController.class);

    @Autowired
    private AnswerRepository answerRepo;

    @Autowired
    private QuestionRepository questionRepo;

    @PostMapping
    public Answer create(@PathVariable Long questionId, String contents, HttpSession session) {
        log.info("answer contents : {}", contents);
        Question question = questionRepo.findById(questionId).get();
        Answer answer = Answer.builder().user(HttpSessionUtils.getUserFromSession(session).get()).question(question).contents(contents).build();
        question.addAnswer();
        return answerRepo.save(answer);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long questionId,  @PathVariable Long id, HttpSession session) {
        Answer answer = answerRepo.findById(id).get();
        Result result = answer.delete(HttpSessionUtils.getUserFromSession(session));
        answerRepo.save(answer);
        Question question = questionRepo.findById(questionId).get();
        question.deleteAnswer();
        questionRepo.save(question);
        return result;
    }
}
