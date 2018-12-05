package codesquad.domain.qna.comment;

import codesquad.domain.qna.comment.dao.CommentRepository;
import codesquad.domain.util.UrlFormat;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping("/api/comment")
public class ApiCommentController {
    @Autowired
    private CommentRepository commentRepository;

    private static final Logger logger = getLogger(ApiCommentController.class);

    public ApiCommentController() {

    }

    @PostMapping()
    @ResponseBody
    public Comment writeComment(Comment comment) {
        logger.info("QnA 댓글 작성!");
        commentRepository.save(comment);
        return comment;
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public Long deleteComment(@PathVariable Long id, Comment comment) {
        logger.info("QnA 댓글 삭제!");
        commentRepository.deleteById(id);
        return id;
    }

    @GetMapping("/{commentId}")
    @ResponseBody
    public Comment updatateCommentForm(@PathVariable Long commentId) {
        logger.info("QnA 댓글 수정 화면 이동!");
        return commentRepository.findById(commentId).orElse(null);
    }

    @PutMapping("/{commentId}")
    @ResponseBody
    public Comment updateComment(@PathVariable Long commentId, Comment updatedComment) {
        logger.info("QnA 댓글 수정!");
        Comment comment = commentRepository.findById(commentId).orElse(null);
        comment.updateComment(updatedComment);
        commentRepository.save(comment);
        return comment;
    }
}
