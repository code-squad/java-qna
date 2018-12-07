package codesquad.domain.qna;

import codesquad.domain.qna.comment.dao.CommentRepository;
import codesquad.domain.qna.dao.QuestionRepository;
import codesquad.domain.util.Result;
import codesquad.domain.util.Session;
import codesquad.domain.util.SessionMaintenanceException;
import codesquad.domain.util.UrlFormat;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

import static org.slf4j.LoggerFactory.getLogger;

@RequestMapping("/questions")
@Controller
public class QuestionController {
    private static final Logger logger = getLogger(QuestionController.class);

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/inquireForm")
    public String ask(HttpSession httpSession, Model model) {
        logger.info("QnA 질문작성 페이지 이동!");
        try {
            Session.isSession(httpSession);
            return "/qna/form";
        } catch (SessionMaintenanceException sme) {
            model.addAttribute("result", Result.fail("로그인이 필요한 서비스입니다."));
            return "/user/login_failed";
        }
    }

    @PostMapping()
    public String inquire(Question question, Model model, HttpSession httpSession) {
        try {
            Session.isSession(httpSession);
            logger.info("QnA 질문 작성!");
            questionRepository.save(question);
            return "redirect:/";
        } catch (SessionMaintenanceException sme) {
            model.addAttribute("result", Result.fail("로그인이 필요한 서비스입니다!"));
            return "/user/login_failed";
        }
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model, HttpSession httpSession) {
        logger.info("QnA 질문 상세보기!");
        try {
            Question question = questionRepository.findById(id).orElse(null);
            question.identificationComment(httpSession);
            model.addAttribute("question", question);
            model.addAttribute("commentsCount", question.getCommentsCount());
            model.addAttribute("isQuestionUser", Session.isInPerson(question, httpSession));
        } catch (SessionMaintenanceException sme) {
            model.addAttribute("isWrite", "disabled");
        }
        return "/qna/show";
    }

    @GetMapping("/{id}/inquireForm")
    public String modify(@PathVariable Long id, Model model, HttpSession httpSession) {
        try {
            Session.isInPerson(questionRepository.findById(id).orElse(null), httpSession);
            logger.info("QnA 질문 수정화면 이동!");
            model.addAttribute("question", questionRepository.findById(id).orElse(null));
            return "/qna/modify";
        } catch (SessionMaintenanceException sme) {
            model.addAttribute("result", Result.fail("로그인이 필요한 서비스입니다!"));
            return "/user/login_failed";
        }
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, Model model, Question updatedQuestion, HttpSession httpSession) {
        try {
            Question question = questionRepository.findById(id).orElse(null);
            Session.isInPerson(question, httpSession);
            question.updateQuestion(updatedQuestion);
            questionRepository.save(question);
            logger.info("QnA 질문 수정!");
            model.addAttribute("question", question);
            return UrlFormat.urlConverter("/qna/", id);
        } catch (SessionMaintenanceException sme) {
            model.addAttribute("result", Result.fail("로그인이 필요한 서비스입니다!"));
            return "/user/login_failed";
        }
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, Model model, HttpSession httpSession) {
        try {
            Question question = questionRepository.findById(id).orElse(null);
            Session.isInPerson(question, httpSession);
            logger.info("QnA 삭제!");
            question.removeComment();
            question.deletionProcess();
            questionRepository.save(question);
            return "redirect:/";
        } catch (SessionMaintenanceException sme) {
            model.addAttribute("result", Result.fail("로그인이 필요한 서비스입니다!"));
            return "/user/login_failed";
        }

    }
}
