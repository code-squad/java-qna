package codesquad.answer;

import codesquad.HttpSessionUtils;
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
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }
        questionRepository.findById(questionId).map(qna ->
                answerRepository.save(new Answer(qna, HttpSessionUtils.getUserFormSession(session), contents))
        ).orElse(null);
        return "redirect:/questions/{questionId}";
    }


    @GetMapping("/{id}")
    public String updateForm(@PathVariable long id, HttpSession session, Model model) {
        logger.info("answer updateForm");
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }
        User sessionUser = HttpSessionUtils.getUserFormSession(session);
        Answer answer = answerRepository.findById(id).orElse(null);
        if (!answer.matchSessionUser(sessionUser)) {
            session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
            return "/user/login_failed";
        }
        model.addAttribute("answer", answer);
        return "/qna/updateAnswer";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable long id, Answer updatedAnswer) {
        logger.info("answer update");
        logger.debug("update answer: {}",updatedAnswer);
        Answer answer = answerRepository.findById(id).orElse(null);
        logger.debug("update answer: {}",answer);
        answer.update(updatedAnswer);
        answerRepository.save(answer);
        /*answerRepository.findById(id).map(answer -> {
            answer.update(updatedAnswer);
            return answerRepository.save(answer);
        }).orElse(null);*/
        return "redirect:/questions/{questionId}";
    }

    @DeleteMapping("/{id}")
    public String delete(HttpSession session, @PathVariable long id) {
        logger.info("answer delete");
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }
        User sessionUser = HttpSessionUtils.getUserFormSession(session);
        Answer answer = answerRepository.findById(id).orElse(null);

        //아이디와 질문아이디와 다를경우 로그아웃 하고 로그인 화면 띄움
        if (!answer.matchSessionUser(sessionUser)) {
            session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
            return "/user/login_failed";
        }
        answerRepository.delete(answer);
        return "redirect:/questions/{questionId}";
    }


}
