package codesquad.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    Optional<Question> findQuestionById(Long id);

    List<Question> findQuestionsByDeletedFalseOrderByIdDesc();

    Question findQuestionByAnswersContaining(Answer answer);
}
