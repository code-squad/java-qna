package com.codessquad.qna.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface AnswerRepository extends CrudRepository<Answer, Long> {
    ArrayList<Answer> findByQuestionId(Long questionId);
}
