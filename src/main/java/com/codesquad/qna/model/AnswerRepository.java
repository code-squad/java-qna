package com.codesquad.qna.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnswerRepository extends CrudRepository<Answer, Long> {
    List<Answer> findByQuestionIdAndDeletedFalse(Long questionId);
}
