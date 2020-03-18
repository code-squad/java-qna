package com.codessquad.qna.domain;

import com.codessquad.qna.domain.Answer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends CrudRepository<Answer, Long> {

    List<Answer> findByQuestionId(Long questionId);

    @Query("SELECT answer FROM Answer answer WHERE  answer.deleted = false and answer.question.id = :questionId ORDER BY answer.createdAt DESC")
    List<Answer> findActiveAnswerByQuestionId(Long questionId);

    @Query("SELECT answer FROM Answer answer WHERE answer.deleted = false and answer.id = :id")
    Optional<Answer> findActiveAnswerById(Long id);
}

