package codesquad.answer;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnswerRepository extends CrudRepository<Answer, Long> {
    List<Answer> findByQuestionId(Long id);

    List<Answer> findByQuestionIdAndDeleted(Long id, boolean status);
}
