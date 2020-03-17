package com.codessquad.qna.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import javax.transaction.Transactional;


public interface QuestionRepository extends JpaRepository<Question, Long> {
    Page<Question> findAllByDeletedFalse(Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Question SET deleted=true WHERE id=:id")
    void delete(Long id);
}
