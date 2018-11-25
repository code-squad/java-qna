

package codesquad.question;

import codesquad.HttpSessionUtils;
import codesquad.answer.AnswerRepository;
import codesquad.exception.QuestionException;
import codesquad.user.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Collection;


@Controller
@RequestMapping("/questions")
public class QuestionController {
    private static final Logger logger = LogManager.getLogger(QuestionController.class);
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @GetMapping("/form")
    public String questions(HttpSession session,Model model) {
        logger.info("질문하기");
        model.addAttribute("User",HttpSessionUtils.getUserFormSession(session));
        return "/qna/form";
    }

    @PostMapping("")
    public String questions(String title,String contents, HttpSession session) {
        logger.info("qna 인스턴스 생성");
        User loginUser = HttpSessionUtils.getUserFormSession(session);
        Question newQuestion = new Question(loginUser,title,contents);

        questionRepository.save(newQuestion);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String profile(Model model, @PathVariable long id) {
        logger.info("질문 상세 페이지");
        Question question = questionRepository.findById(id).orElseThrow(QuestionException::new);

        model.addAttribute("question", question);

        return "/qna/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(Model model, @PathVariable long id, HttpSession session) {
        logger.info("qna 수정폼");

        Question question = getMatchingQuestion(session, id);

        model.addAttribute("question", question);
        return "qna/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable long id, Question newQuestion) {
        logger.info("update");
        Question question = questionRepository.findById(id).orElseThrow(QuestionException::new);
        question.update(newQuestion);
        questionRepository.save(question);
        return "redirect:/questions/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(HttpSession session, @PathVariable long id){
        logger.info("delete");

        Question question = getMatchingQuestion(session, id);

        questionRepository.delete(question);
        return "redirect:/";
    }

    private Question getMatchingQuestion(HttpSession session, @PathVariable long id) {
        User loginUser = HttpSessionUtils.getUserFormSession(session);
        Question question = questionRepository.findById(id).orElseThrow(QuestionException::new);
        question.matchWrite(loginUser);
        return question;
    }

}
