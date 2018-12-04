package codesquad.qna.answers;

import codesquad.qna.questions.Question;
import codesquad.qna.questions.QuestionRepository;
import codesquad.user.User;
import codesquad.util.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import support.core.Result;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {
    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    QuestionRepository questionRepository;

    @PostMapping
    public Result<Answer> create(@PathVariable Long questionId, String contents, HttpSession session){
        if(!HttpSessionUtils.existLoginUserFromSession(session)) {
            return Result.error("permission denied. 로그인이 필요합니다.");
        }
        User loginUser = HttpSessionUtils.getLoginUserFromSession(session);
        Question theQuestion = questionRepository.findById(questionId).orElse(null);
        Answer theAnswer = new Answer(loginUser, theQuestion, contents);
        return Result.ok(answerRepository.save(theAnswer));
    }

    @DeleteMapping("{id}")
    public Result deleteAnswer(@PathVariable long id, HttpSession session){
        if(!HttpSessionUtils.existLoginUserFromSession(session)) {
            return Result.error("permission dined. 로그인이 필요합니다.");
        }
        Answer theAnswer = answerRepository.findById(id).orElse(null);
        try{
            theAnswer.delete(HttpSessionUtils.getLoginUserFromSession(session));
        } catch (IllegalStateException e){
            return Result.error(e.getMessage());
        }
        return Result.ok(answerRepository.save(theAnswer));
    }
}
