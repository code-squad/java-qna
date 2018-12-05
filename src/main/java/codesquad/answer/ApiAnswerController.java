package codesquad.answer;

import codesquad.qna.Question;
import codesquad.qna.QuestionRepository;
import codesquad.result.Result;
import codesquad.user.User;
import codesquad.utility.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/qna/{questionId}/answers")
public class ApiAnswerController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping("")
    public Answer create(@PathVariable Long questionId, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return null;
        }

        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(questionId).orElse(null);
        question.increaseAnswerCount();
        Answer answer = new Answer(loginUser, question, contents);
        return answerRepository.save(answer);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        System.out.println("isDelete :    SDddfgkjrlkgjofijlkfjblkjd");
        if (!HttpSessionUtils.isLoginUser(session)) {
            return Result.fail("로그인 해야 합니다.");
        }

        Answer answer = answerRepository.findById(id).orElse(null);
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        if (!answer.isSameWriter(loginUser)) {
            return Result.fail("자신의 글만 삭제 할 수 있습니다.");
        }

        Question question = questionRepository.findById(questionId).orElse(null);
        question.decreaseAnswerCount();
        answer.changeDeletedTrue();
//        questionRepository.save(question);
        answerRepository.save(answer);
        return Result.ok();
    }

}
