package com.codessquad.qna.question;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnswerRepository extends CrudRepository<Answer, Long> {
    List<Answer> findByQuestionId(Long questionId);
    Answer findByQuestionIdAndId(Long questionId, Long answerId);
}
