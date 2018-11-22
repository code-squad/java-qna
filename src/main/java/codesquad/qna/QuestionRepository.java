package codesquad.qna;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface QuestionRepository extends CrudRepository<Question, Long> {
    Optional<Question> findByIdAndWriter(Long id, String writer);
}
