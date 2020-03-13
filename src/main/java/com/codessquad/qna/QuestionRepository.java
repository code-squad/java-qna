package com.codessquad.qna;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends CrudRepository<Question, Long> {

    @Query("SELECT question FROM Question question WHERE question.deleted = false")
    List<Question> findAllActiveQuestion();

    @Query("SELECT question FROM Question question WHERE question.deleted = false and question.id = :id")
    Optional<Question> findActiveQuestionById(Long id);
}
