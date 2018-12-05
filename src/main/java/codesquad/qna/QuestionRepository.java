package codesquad.qna;

import codesquad.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends CrudRepository<Question,Long> {
    List<Question> findByDeleted(boolean deleted);
}
