package com.codessquad.qna.question;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface QuestionRepository extends CrudRepository<Question, Long> {
  @Transactional(readOnly = true)
  Optional<Question> findByIdAndDeleted(Long questionId, boolean deleted);

  @Transactional(readOnly = true)
  Iterable<Question> findAllByDeleted(boolean deleted);
}
