package codesquad.answer;

import codesquad.question.Question;
import org.springframework.data.repository.CrudRepository;


public interface AnswerRepository extends CrudRepository<Answer, Long> {
    Iterable<Answer> findByQuestion(Question question);
}
