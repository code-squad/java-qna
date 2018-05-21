package codesquad.domain.repository;

import codesquad.domain.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long>{

    List<Answer> findAllByQuestionId(Long id);

    Optional<Answer> findById(Long id);

    @Transactional
    void deleteById(Long id);
}
