package com.codessquad.qna.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import javax.transaction.Transactional;
import java.util.List;

public interface AnswerRepository extends CrudRepository<Answer, Long> {

    List<Answer> findByQuestionIdAndDeletedFalse(Long questionId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Answer SET deleted=true WHERE question=:question")
    void deleteByQuestion(Question question);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Answer SET deleted=true WHERE id=:id")
    void delete(Long id);
}
