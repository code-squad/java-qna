package codesquad.domain.comment;

import codesquad.domain.comment.dao.CommentRepository;
import codesquad.domain.qna.Question;
import codesquad.domain.qna.dao.QnARepository;
import codesquad.domain.util.UrlFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private QnARepository qnARepository;

    public CommentController() {

    }

    @PostMapping("/comment/write/{id}")
    public String writeComment(@PathVariable Long id, Comment comment) {
        System.out.println("QnA 댓글 작성!");
        System.out.println(comment.toString());
        commentRepository.save(comment);
        Question question = qnARepository.findById(id).orElse(null);
        question.operateComment(1);
        qnARepository.save(question);
        return UrlFormat.urlConverter("/qna/show/", id);
    }

    @DeleteMapping("/comment/delete/{questionId}/{id}")
    public String deleteComment(@PathVariable Long questionId, @PathVariable Long id, Comment comment) {
        System.out.println("QnA 댓글 삭제!");
        commentRepository.deleteById(questionId);
        Question question = qnARepository.findById(id).orElse(null);
        question.operateComment(-1);
        qnARepository.save(question);
        return UrlFormat.urlConverter("/qna/show/", id);
    }

    @GetMapping("comment/modify/{questionId}/{id}")
    public String modify(@PathVariable Long questionId, @PathVariable Long id) {
        System.out.println("QnA 댓글 수정 화면으로 이동!");
        return "/comment/modify";
    }

    @PutMapping("/comment/update/{questionId}/{id}")
    public String updateComment(@PathVariable Long questionId, @PathVariable Long id, Comment updatedComment) {
        System.out.println("QnA 댓글 수정!");
        Comment comment = commentRepository.findById(id).orElse(null);
        comment.updateComment(updatedComment);
        commentRepository.save(comment);
        return UrlFormat.urlConverter("/qna/show/", id);
    }
}
