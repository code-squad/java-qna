package codesquad.answer;

import codesquad.qna.Question;
import codesquad.user.User;
import org.springframework.data.repository.CrudRepository;

public interface AnswerRepository extends CrudRepository<Answer,Long>{
}
