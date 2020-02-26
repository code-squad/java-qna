package com.codesquad.qna.domain;

import org.springframework.data.repository.CrudRepository;

public interface AnswerRepository extends CrudRepository<Answer, Long> {
    Iterable<Answer> findByQuestionQuestionId(Long questionId);
}
