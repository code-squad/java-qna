package codesquad.domain.qna.dao;

import codesquad.domain.qna.Question;
import codesquad.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QnARepository extends JpaRepository<Question, Long> {
    List<Question> findByDeleted(Boolean deleted);
}
