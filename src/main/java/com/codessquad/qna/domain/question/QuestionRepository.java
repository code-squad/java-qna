package com.codessquad.qna.domain.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Iterable<Question> findAllByIsDeletedFalseOrderByCreatedDateTimeDesc();

    List<Question> findAllByIsDeletedFalse();

    Page<Question> findAllByIsDeletedFalse(Pageable pageable);

}
