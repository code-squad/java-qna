package codesquad.domain.comment.dao;

import codesquad.domain.comment.Comment;
import codesquad.domain.qna.Question;
import codesquad.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    //@Query("select c from Comment c where c.question = :id")
    List<Comment> findByQuestion(Question question);
}

