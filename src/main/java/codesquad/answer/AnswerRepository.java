package codesquad.answer;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnswerRepository extends CrudRepository<Answer, Long> {
    Iterable<Answer> findByUserId(Long userId);
}
