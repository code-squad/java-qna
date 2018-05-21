package codesquad.domain.repository;

import codesquad.domain.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findAllByOrderByIdDesc();

    Optional<Question> findById(Long id);

    @Transactional
    void deleteById(Long id);
}
