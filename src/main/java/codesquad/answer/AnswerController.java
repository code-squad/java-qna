package codesquad.answer;

import codesquad.HttpSessionUtils;
import codesquad.exception.AnswerException;
import codesquad.question.QuestionRepository;
import codesquad.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
    private static Logger logger = LoggerFactory.getLogger(AnswerController.class);
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionRepository questionRepository;


    @PostMapping()
    public String create(HttpSession session, @PathVariable long questionId, String contents) {
        logger.info("answer info");
        HttpSessionUtils.isLoginUser(session);
        questionRepository.findById(questionId).map(qna ->
                answerRepository.save(new Answer(qna, HttpSessionUtils.getUserFormSession(session), contents))
        ).orElse(null);
        return "redirect:/questions/{questionId}";
    }


    @GetMapping("/{id}")
    public String updateForm(@PathVariable long id, HttpSession session, Model model) {
        logger.info("answer updateForm");

        Answer answer = getMatchingAnswer(session, id);

        model.addAttribute("answer", answer);
        return "/qna/updateAnswer";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable long id, Answer updatedAnswer) {
        logger.info("answer update");
        Answer answer = answerRepository.findById(id).orElseThrow(AnswerException::new);
        answer.update(updatedAnswer);
        answerRepository.save(answer);
        return "redirect:/questions/{questionId}";
    }

    @DeleteMapping("/{id}")
    public String delete(HttpSession session, @PathVariable long id) {
        logger.info("answer delete");

        Answer answer = getMatchingAnswer(session, id);

        answerRepository.delete(answer);
        return "redirect:/questions/{questionId}";
    }

    private Answer getMatchingAnswer(HttpSession session, @PathVariable long id) {
        User sessionUser = HttpSessionUtils.getUserFormSession(session);
        Answer answer = answerRepository.findById(id).orElseThrow(AnswerException::new);
        answer.matchSessionUser(sessionUser);
        return answer;
    }


}
