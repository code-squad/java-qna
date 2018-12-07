package codesquad.domain.qna.comment;

import codesquad.domain.qna.comment.dao.CommentRepository;
import codesquad.domain.util.Result;
import codesquad.domain.util.Session;
import codesquad.domain.util.SessionMaintenanceException;
import codesquad.domain.util.UrlFormat;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/api/comments")
public class ApiCommentController {
    @Autowired
    private CommentRepository commentRepository;

    private static final Logger logger = getLogger(ApiCommentController.class);

    public ApiCommentController() {

    }

    @PostMapping()
    public Comment writeComment(Comment comment, HttpSession httpSession) {
        try {
            Session.isSession(httpSession);
            logger.info("QnA 댓글 작성!");
            commentRepository.save(comment);
            return comment;
        } catch (SessionMaintenanceException sme) {
            return null;
        }
    }

    @DeleteMapping("/{commentId}")
    public Long deleteComment(@PathVariable Long commentId) {
        logger.info("QnA 댓글 삭제!");
        Comment comment = commentRepository.findById(commentId).orElse(null);
        comment.setDeleted(true);
        commentRepository.save(comment);
        return commentId;
    }

    @GetMapping("/{commentId}")
    public Comment updatateCommentForm(@PathVariable Long commentId) {
        logger.info("QnA 댓글 수정 화면 이동!");
        return commentRepository.findById(commentId).orElse(null);
    }

    @PutMapping("/{commentId}")
    public Comment updateComment(@PathVariable Long commentId, Comment updatedComment) {
        logger.info("QnA 댓글 수정!");
        Comment comment = commentRepository.findById(commentId).orElse(null);
        comment.updateComment(updatedComment);
        commentRepository.save(comment);
        return comment;
    }
}
