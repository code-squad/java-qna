package codesquad.question;

import org.springframework.data.repository.CrudRepository;

public interface QnaRepository extends CrudRepository<Question, Long> {
}
