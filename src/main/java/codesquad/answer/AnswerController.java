package codesquad.answer;

import codesquad.question.Question;
import codesquad.question.QuestionRepository;
import codesquad.utils.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{questionPId}/answer")
public class AnswerController {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("")
    public String create(@PathVariable long questionPId, Answer answer, HttpSession session) {
        Question question = questionRepository.findById(questionPId).get();
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/users/login";
        }
        answerRepository.save(answer);
        question.plusAnswersSize();
        return String.format("redirect:/questions/%d", questionPId);
    }

    @DeleteMapping("/{answerPId}")
    public String delete(@PathVariable long answerPId, @PathVariable long questionPId, HttpSession session) {
        Answer answer = answerRepository.findById(answerPId).get();
        Question question = questionRepository.findById(questionPId).get();
        if (!HttpSessionUtils.isValid(session, answer)) {
            return String.format("redirect:/questions/%d", questionPId);
        }
        answer.delete(HttpSessionUtils.getUserFromSession(session));
        question.minusAnswersSize();
        answerRepository.save(answer);
        return String.format("redirect:/questions/%d", questionPId);
    }


//    @GetMapping("/{answerPId}")
//    public String update(@PathVariable long answerPId, @PathVariable long questionPId, HttpSession session) {
//        Answer answer = answerRepository.findById(answerPId).get();
//        if (!HttpSessionUtils.isValid(session, answer)) {
//            throw new IllegalArgumentException("에러!");
//        }

//        return String.format("redirect:/questions/%d", questionPId);
//    }
}
