package codesquad.qna;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.*;

public interface QuestionRepository extends CrudRepository<Question, Long> {
    List<Question> findByDeleted(boolean deleted);
}
