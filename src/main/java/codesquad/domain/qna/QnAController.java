package codesquad.domain.qna;

import codesquad.domain.comment.Comment;
import codesquad.domain.comment.CommentController;
import codesquad.domain.comment.dao.CommentRepository;
import codesquad.domain.qna.dao.QnARepository;
import codesquad.domain.user.User;
import codesquad.domain.util.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class QnAController {

    @Autowired
    private QnARepository qnARepository;
    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/qna/ask")
    public String ask(HttpSession httpSession) {
        System.out.println("QnA 질문작성 페이지 이동!");
        if(!Session.isSession(httpSession)) {
            return "redirect:/user/login";
        }
        return "/qna/form";
    }

    @PostMapping("/qna/inquire")
    public String inquire(Question question) {
        System.out.println("QnA 질문 작성!");
        qnARepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/qna/show/{id:.+}")
    public String detail(@PathVariable Long id, Model model, HttpSession httpSession) {
        System.out.println("QnA 질문 상세보기!");
        Question question = qnARepository.findById(id).orElse(null);
        List<Comment> comments = commentRepository.findByQuestion(question);
        model.addAttribute("question", question);
        model.addAttribute("comments", comments);
        model.addAttribute("commentsCount", comments.size());
        model.addAttribute("isSession", Session.isSession(httpSession));
        return "/qna/show";
    }

    @GetMapping("/qna/modify/{id}")
    public String modify(@PathVariable Long id, Model model, HttpSession httpSession) {
        if(!Session.isSession(httpSession)) {
            return "redirect:/user/login";
        }
        System.out.println("QnA 질문 수정화면 이동!");
        model.addAttribute("question", qnARepository.findById(id).orElse(null));
        return "/qna/modify";
    }

    @PutMapping("/qna/update/{id}")
    public String update(@PathVariable Long id, Model model, HttpSession httpSession, Question updatedQuestion) {
        if(!Session.isSession(httpSession)) {
            return "redirect:/user/login";
        }
        Question question = qnARepository.findById(id).orElse(null);
        question.updateQuestion(updatedQuestion);
        qnARepository.save(question);
        System.out.println("QnA 질문 수정!");
        model.addAttribute("question", question);
        return "redirect:/qna/show/" + Long.valueOf(id);
    }

    @DeleteMapping("/qna/delete/{id}")
    public String delete(@PathVariable Long id, HttpSession httpSession) {
        if(!Session.isSession(httpSession)) {
            return "redirect:/user/login";
        }
        System.out.println("QnA 삭제!");
        qnARepository.deleteById(id);
        /* 댓글도 삭제 */
        return "redirect:/";
    }
}
