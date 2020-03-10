package com.codessquad.qna.answer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AnswerRepository extends CrudRepository<Answer, Long> {
  @Transactional(readOnly = true)
  Optional<Answer> findByIdAndDeleted(Long id, boolean deleted);

  @Transactional(readOnly = true)
  Iterable<Answer> findByQuestionIdAndDeleted(Long questionId, boolean Deleted);
}
