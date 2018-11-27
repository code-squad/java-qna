package codesquad.qna;

import org.springframework.data.repository.CrudRepository;


public interface QuestionRepository extends CrudRepository<Question, Long> {
    Iterable<Question> findByUserId(Long userId);
    Iterable<Question> findAllByDeleted(boolean bool);
}
