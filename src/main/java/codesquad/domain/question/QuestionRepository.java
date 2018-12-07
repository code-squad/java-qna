package codesquad.domain.question;

import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<Question, Long> {
    Iterable<Question> findAllByDeleted(boolean deleted);
}
