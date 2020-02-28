package com.codessquad.qna;

import org.springframework.data.repository.CrudRepository;

public interface AnswerRepository extends CrudRepository<Answer, Long> {
    Answer[] findByQuestionId(Long questionId);
}

