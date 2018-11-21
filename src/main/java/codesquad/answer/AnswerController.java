package codesquad.answer;


import codesquad.exception.AnswerIdNotMatchException;
import codesquad.exception.QuestionIdNotMatchException;
import codesquad.qna.QuestionRepository;
import codesquad.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping()
    public String create(@PathVariable("questionId") Long questionId, String contents, HttpSession session) {
        if(!SessionUtil.isLoginUser(session)) return "redirect:/users/login";

        questionRepository.findById(questionId).map(question -> {
            Answer answer = new Answer(question, SessionUtil.getUserFromSesssion(session), contents);
            return answerRepository.save(answer);
        }).orElseThrow(QuestionIdNotMatchException::new);

        return "redirect:/questions/" + questionId;
    }

    @DeleteMapping("/{answerId}")
    public String delete(@PathVariable("questionId") Long question_id, @PathVariable("answerId") Long answerId, HttpSession session) {
        if(!questionRepository.existsById(question_id)) throw new QuestionIdNotMatchException("QUESTION_ID IS NOT CORRECT");

        Answer answer = answerRepository.findById(answerId).orElseThrow(AnswerIdNotMatchException::new);
        if(!SessionUtil.permissionCheck(session, answer.getUser())) return "redirect:/users/login";
        answerRepository.delete(answer);
        return  "redirect:/questions/" + question_id;
    }

}
