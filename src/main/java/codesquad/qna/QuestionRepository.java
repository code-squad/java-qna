package codesquad.qna;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface QuestionRepository extends PagingAndSortingRepository<Question, Long> {
    Iterable<Question> findByUserId(Long userId);
    Page<Question> findAllByDeleted(boolean bool, Pageable pageable);
}
