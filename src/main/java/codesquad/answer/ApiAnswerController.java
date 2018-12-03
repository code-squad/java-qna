package codesquad.answer;

import codesquad.utils.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController //json변환
@RequestMapping("/api/question/{questionPId}/answer")
public class ApiAnswerController {
    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping("")
    public Answer create(Answer answer, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            System.out.println("로그인 사용자 없음!!");
            return null;
        }
        return answerRepository.save(answer);
    }

    @DeleteMapping("/{answerPId}")
    public Result delete(@PathVariable long answerPId, HttpSession session) {
        Answer answer = answerRepository.findById(answerPId).get();
        if(!HttpSessionUtils.isLoginUser(session)){
            return Result.fail("로그인 해야 합니다.");
        }
        if (!HttpSessionUtils.isValid(session, answer)) {
            return Result.fail("자신의 글만 삭제할 수 있습니다.");
        }
        answer.delete();
        System.out.println("작동!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        return Result.ok();
    }
}
