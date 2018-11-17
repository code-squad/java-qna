package codesquad.qna;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public interface QuestionRepository extends CrudRepository<Question, Long> {
}
