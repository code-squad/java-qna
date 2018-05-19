package codesquad.web.domain.repository;

import codesquad.web.domain.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long>{

    List<Answer> findAllByQuestion_Id(Long id);

}
