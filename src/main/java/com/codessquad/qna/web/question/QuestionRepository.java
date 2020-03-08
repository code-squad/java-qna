package com.codessquad.qna.web.question;

import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<Question, Long> {
    Iterable<Question> findAllByIsDeletedFalseOrderByCreatedDateTimeDesc();
}
