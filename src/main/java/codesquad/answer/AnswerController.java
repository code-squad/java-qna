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
@RequestMapping("/questions/{question_id}/answers")
public class AnswerController {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping()
    public String create(@PathVariable("question_id") Long question_id, String contents, HttpSession session) {

        System.out.println("왔섭!!!");
        if(!SessionUtil.isLoginUser(session)) return "redirect:/users/login";


        questionRepository.findById(question_id).map(question -> {
            Answer answer = new Answer(question, SessionUtil.getUserFromSesssion(session), contents);
            return answerRepository.save(answer);
        }).orElseThrow(QuestionIdNotMatchException::new);

        return "redirect:/questions/" + question_id;
    }

    @DeleteMapping("/{answer_id}")
    public String delete(@PathVariable("question_id") Long question_id, @PathVariable("answer_id") Long answer_id, HttpSession session) {
        if(!questionRepository.existsById(question_id)) throw new QuestionIdNotMatchException("QUESTION_ID IS NOT CORRECT");

        Answer answer = answerRepository.findById(answer_id).orElseThrow(AnswerIdNotMatchException::new);
        if(!SessionUtil.permissionCheck(session, answer.getUser())) return "redirect:/users/login";
        answerRepository.delete(answer);
        return  "redirect:/questions/" + question_id;
    }

}
