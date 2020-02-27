package com.codesquad.qna.model;

import org.springframework.data.repository.CrudRepository;

public interface AnswerRepository extends CrudRepository<Answer, Long> {
    Iterable<Answer> findByQuestionId(Long questionId);
    long countByQuestionId(Long questionId);
}
