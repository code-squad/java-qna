package codesquad.web;

import codesquad.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {
    private static final Logger log = LoggerFactory.getLogger(ApiAnswerController.class);

    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("")
    public Answer create(@PathVariable Long questionId, HttpSession session, Answer answer){
        if (!HttpSessionUtils.isLoginUser(session)) {
            return null;
        }
        User user = HttpSessionUtils.getSessionedUser(session);
        answer.setWriter(user);
        Question question = questionRepository.findOne(questionId);
        log.debug("Save");
        return answerRepository.save(answer);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session){
        log.debug("Delete");
        if(!HttpSessionUtils.isLoginUser(session)){
            return Result.fail("You need login");
        }
        User user = HttpSessionUtils.getSessionedUser(session);
        Answer answer = answerRepository.findOne(id);
        if (!user.isSameWriterOfAnswer(answer)) {
            return Result.fail("You can't update,delete another user's answer");
        }
        answerRepository.delete(id);
        return Result.ok();
    }
}
