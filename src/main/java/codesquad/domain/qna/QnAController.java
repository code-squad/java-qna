package codesquad.domain.qna;

import codesquad.domain.qna.comment.dao.CommentRepository;
import codesquad.domain.qna.dao.QnARepository;
import codesquad.domain.util.Session;
import codesquad.domain.util.UrlFormat;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

import static org.slf4j.LoggerFactory.getLogger;

@RequestMapping("/qna")
@Controller
public class QnAController {
    private static final Logger logger = getLogger(QnAController.class);

    @Autowired
    private QnARepository qnARepository;

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/inquireForm")
    public String ask(HttpSession httpSession) {
        logger.info("QnA 질문작성 페이지 이동!");
        if(!Session.isSession(httpSession)) {
            return "redirect:/user/loginForm";
        }
        return "/qna/form";
    }

    @PostMapping()
    public String inquire(Question question) {
        logger.info("QnA 질문 작성!");
        qnARepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model, HttpSession httpSession) {
        logger.info("QnA 질문 상세보기!");
        Question question = qnARepository.findById(id).orElse(null);
        question.identificationComment(httpSession);
        model.addAttribute("question", question);
        model.addAttribute("commentsCount", question.getCommentsCount());
        model.addAttribute("isQuestionUser", Session.isUser(httpSession, question));
        return "/qna/show";
    }

    @GetMapping("/{id}/inquireForm")
    public String modify(@PathVariable Long id, Model model, HttpSession httpSession) {
        if(!Session.isSession(httpSession)) {
            return "redirect:/user/loginForm";
        }
        logger.info("QnA 질문 수정화면 이동!");
        model.addAttribute("question", qnARepository.findById(id).orElse(null));
        return "/qna/modify";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, Model model, HttpSession httpSession, Question updatedQuestion) {
        if(!Session.isSession(httpSession)) {
            return "redirect:/user/loginForm";
        }
        Question question = qnARepository.findById(id).orElse(null);
        question.updateQuestion(updatedQuestion);
        qnARepository.save(question);
        logger.info("QnA 질문 수정!");
        model.addAttribute("question", question);
        return UrlFormat.urlConverter("/qna/", id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession httpSession) {
        if(!Session.isSession(httpSession)) {
            return "redirect:/user/loginForm";
        }
        logger.info("QnA 삭제!");
        Question question = qnARepository.findById(id).orElse(null);
        question.deletionProcess();
        qnARepository.save(question);
        return "redirect:/";
    }
}
