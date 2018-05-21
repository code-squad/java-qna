package codesquad.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findAllByOrderByQuestionIdDesc();

    Question findQuestionByQuestionId(Long questionId);
}
