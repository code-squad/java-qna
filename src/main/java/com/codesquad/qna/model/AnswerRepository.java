package com.codesquad.qna.model;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface AnswerRepository extends CrudRepository<Answer, Long> {
    Iterable<Answer> findByQuestionId(Long questionId);

    long countByQuestionId(Long questionId);

    @Transactional
    @Modifying
    @Query("delete from Answer a where a.question.id in :id")
    void deleteAllByIdInQuery(@Param("id") Long id);
}
