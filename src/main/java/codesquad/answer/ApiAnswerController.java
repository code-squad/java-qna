package codesquad.answer;

import codesquad.question.Question;
import codesquad.question.QuestionRepository;
import codesquad.utils.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import surpport.Result;

import javax.servlet.http.HttpSession;

@RestController //json변환
@RequestMapping("/api/questions/{questionPId}/answer")
public class ApiAnswerController {
    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("")
    public Answer create(@PathVariable long questionPId, Answer answer, HttpSession session) {
        Question question = questionRepository.findById(questionPId).get();
        if (!HttpSessionUtils.isLoginUser(session)) {
            System.out.println("로그인 사용자 없음!!");
            return null;
        }
        question.plusAnswersSize();
        return answerRepository.save(answer);
    }

    @DeleteMapping("/{answerPId}")
    public Result delete(@PathVariable long answerPId, @PathVariable long questionPId, HttpSession session) {
        Answer answer = answerRepository.findById(answerPId).get();
        Question question = questionRepository.findById(questionPId).get();
        if (!HttpSessionUtils.isValid(session, answer)) {
            return Result.fail("다른사람의 답변을 수정 또는 삭제할 수 없습니다.");
        }
        answer.delete(HttpSessionUtils.getUserFromSession(session));
        question.minusAnswersSize();
        answerRepository.save(answer);
        return Result.ok();
    }
}
