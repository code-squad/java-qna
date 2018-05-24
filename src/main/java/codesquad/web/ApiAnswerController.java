package codesquad.web;

import codesquad.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

// RestController를 사용을 하면 data의 타입을 json형태로 바꿔준다
@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {
    public static final Logger logger = LoggerFactory.getLogger(ApiAnswerController.class);

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;
    // Answer는 항상 Question에 속해있다. 따라서 Question이 없으면 Answer 또한 없다. 종속관계에 있다. 항상 question ID가 필요하다.

    @PostMapping("")
    public Answer create(@PathVariable Long questionId, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session))
            return null;

        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findOne(questionId);
        Answer answer = new Answer(loginUser, question, contents);
        question.addAnswer();
        return answerRepository.save(answer);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long questionId , @PathVariable Long id, HttpSession session) {
        logger.debug("delete 메소드 호출");
        if (!HttpSessionUtils.isLoginUser(session))
            return Result.fail("로그인해야 합니다.");

        Answer answer = answerRepository.findOne(id);
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        if (!answer.isSameWriter(loginUser))
            return Result.fail("자신의 글만 삭제할 수 있습니다.");
        logger.debug("예외처리 패스 id는 {}", id);
        answerRepository.delete(id);
        Question question = questionRepository.findOne(questionId);
        question.deleteAnswer();
        questionRepository.save(question);
        return Result.ok();
    }

}
