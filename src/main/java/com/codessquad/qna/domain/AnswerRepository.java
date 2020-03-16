package com.codessquad.qna.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findAllByQuestionId(Long questionId);

    @Query("SELECT answer FROM Answer answer where answer.deleted= false and answer.question.id= :id")
    List<Answer>  findAllActiveAnswers(@Param("id") Long id);
}
