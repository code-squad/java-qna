package codesquad.web;

import codesquad.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

// answer는 question에 종속적이다
@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {
    private static final Logger log = LoggerFactory.getLogger(ApiAnswerController.class);
    
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping
    public Answer create(@PathVariable Long questionId, String contents, HttpSession session) {
        log.info("들어오나");

        if (!HttpSessionUtils.isLoginUser(session)) {
            log.info("세션이 널이다");

            // TODO null
            return null;
        }

        Answer answer = createAnswerProc(questionId, contents, session);

        // save 먼저?
        return answerRepository.save(answer);
    }

    private Answer createAnswerProc(Long questionId, String contents, HttpSession session) {
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findOne(questionId);
        Answer answer = new Answer(loginUser, question, contents);
        question.addAnswer();

        return answer;
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return Result.fail("로그인 해야 합니다.");
        }

        if (!checkValidSession(id, session)) return Result.fail("자신의 글만 삭제할 수 있습니다.");

        Question question = deleteAnswerProc(questionId, id);
        questionRepository.save(question);

        return Result.ok();
    }

    private boolean checkValidSession(@PathVariable Long id, HttpSession session) {
        // 삭제하려는 답변의 작성자와 현재 세션의 작성자가 같은지 확인
        Answer answer = answerRepository.getOne(id);
        User user = HttpSessionUtils.getUserFromSession(session);
        return answer.checkValidUser(user);
    }

    private Question deleteAnswerProc(@PathVariable Long questionId, @PathVariable Long id) {
        answerRepository.delete(id);
        Question question = questionRepository.findOne(questionId);
        question.deleteAnswer();
        return question;
    }

    @PutMapping("/{id}")
    public Result modify(@PathVariable Long id, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return Result.fail("로그인 해야 합니다.");
        }

        // 수정하려는 답변의 작성자와 현재 세션의 작성자가 같은지 확인
        if (!checkValidSession(id, session)) throw new IllegalStateException("modify error");

        // TODO 답변 수정 구현

        return Result.ok();
    }
}
