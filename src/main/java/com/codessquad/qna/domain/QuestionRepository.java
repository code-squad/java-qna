package com.codessquad.qna.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("SELECT question FROM Question question where question.deleted = false")
    List<Question> findAllByActivedeleted();
}
