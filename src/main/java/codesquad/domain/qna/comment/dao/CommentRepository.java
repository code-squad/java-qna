package codesquad.domain.qna.comment.dao;

import codesquad.domain.qna.comment.Comment;
import codesquad.domain.qna.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    //@Query("select c from Comment c where c.question = :id")
    List<Comment> findByQuestion(Question question);
}

