package codesquad.web;

import codesquad.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("")
    public Answer create(@PathVariable Long questionId, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return null;
        }
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(questionId).get();
        Answer answer = new Answer(loginUser, question, contents);
        question.addAnswer();
        return answerRepository.save(answer);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return Result.fail("로그인 해야 합니다.");
        }

        Answer answer = answerRepository.findById(id).get();
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        if (!answer.isSameWriter(loginUser)) {
            return Result.fail("자신의 글만 삭제할 수 있습니다.");
        }

        answerRepository.deleteById(id);

        Question question = questionRepository.findById(id).get();
        question.deleteAnswer();
        questionRepository.save(question);
        return Result.ok();
    }

}
