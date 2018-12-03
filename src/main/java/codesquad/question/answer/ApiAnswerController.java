package codesquad.question.answer;

import codesquad.config.HttpSessionUtils;
import codesquad.question.QuestionNotFoundException;
import codesquad.question.QuestionRepository;
import codesquad.utils.Result;
import codesquad.user.User;
import codesquad.user.UserNotFoundException;
import codesquad.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping("")
    public ResponseEntity post(@PathVariable long questionId, HttpSession session, String comment) {
        Result result = valid(session);
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if(!result.isValid()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        Answer answer = new Answer(
                questionRepository.findById(questionId).orElseThrow(() -> new QuestionNotFoundException("해당 질문을 찾을 수 없습니다.")),
                userRepository.findById(sessionedUser.getId()).orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다.")),
                comment
        );
        return new ResponseEntity<>(answerRepository.save(answer), HttpStatus.CREATED);
    }

    @DeleteMapping("/{answerId}")
    public ResponseEntity delete(HttpSession session, Model model, @PathVariable long questionId, @PathVariable long answerId) {
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        Answer answer = answerRepository.findById(answerId).orElseThrow(() -> new AnswerNotFoundException("해당 답변을 찾을 수 없습니다."));
        Result result = answer.deleted(sessionedUser);
        if(!result.isValid()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        answerRepository.save(answer);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    private Result valid(HttpSession session) {
        if (!HttpSessionUtils.isLogin(session)) {
            return Result.fail("로그인이 필요합니다.");
        }
        return Result.ok();
    }

    @ExceptionHandler(value = {QuestionNotFoundException.class, UserNotFoundException.class})
    public ResponseEntity exceptionHandler(RuntimeException e) {
        Result result = new Result(false, e.getMessage());
        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }
}
