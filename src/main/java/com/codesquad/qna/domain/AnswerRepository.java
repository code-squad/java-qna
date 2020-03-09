package com.codesquad.qna.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Optional<Answer> findByIdAndId(Long questionId, Long Id);
}
