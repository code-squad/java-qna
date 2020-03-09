package com.codessquad.qna.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface AnswerRepository extends CrudRepository<Answer, Long> {

    List<Answer> findByQuestionIdAndDeletedFalse(Long questionId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Answer SET deleted=true WHERE question.id=question.id")
    void deleteByQuestion(Question question);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Answer SET deleted=true WHERE id=id")
    void delete(@Param("id") Long id);
}
