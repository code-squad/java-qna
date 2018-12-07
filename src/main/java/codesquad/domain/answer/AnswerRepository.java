package codesquad.domain.answer;

import codesquad.domain.question.Question;
import org.springframework.data.repository.CrudRepository;


public interface AnswerRepository extends CrudRepository<Answer, Long> {
    Iterable<Answer> findByQuestion(Question question);
}
