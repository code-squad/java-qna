package com.codessquad.qna.domain.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<Question> findByIdAndIsDeleted(Long id, boolean isDeleted);

    int countByIsDeletedFalse();

    Page<Question> findAllByIsDeletedFalse(Pageable pageable);

}
