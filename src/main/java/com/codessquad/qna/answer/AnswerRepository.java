package com.codessquad.qna.answer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface AnswerRepository extends CrudRepository<Answer, Long> {
  @Transactional(readOnly = true)
  Iterable<Answer> findByQuestionId(Long questionId);
}
