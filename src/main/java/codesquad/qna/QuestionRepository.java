package codesquad.qna;

import codesquad.user.User;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<Question,Long> {
    Question findByWriter(String writer);
}
