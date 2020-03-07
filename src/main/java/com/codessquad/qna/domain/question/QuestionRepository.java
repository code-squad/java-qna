package com.codessquad.qna.domain.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Iterable<Question> findAllByIsDeletedFalseOrderByCreatedDateTimeDesc();

    int countByIsDeletedFalse();

    Page<Question> findAllByIsDeletedFalse(Pageable pageable);

}
