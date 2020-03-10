package com.codessquad.qna;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends CrudRepository<Question, Long> {

    @Query("SELECT q FROM Question q WHERE q.deleted = false")
    List<Question> findAllActiveQuestion();

    @Query("SELECT q FROM Question q WHERE q.deleted = false and q.id = :id")
    Optional<Question> findActiveQuestionById(Long id);
}
