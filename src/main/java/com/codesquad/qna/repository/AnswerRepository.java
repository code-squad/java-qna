package com.codesquad.qna.repository;

import com.codesquad.qna.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Optional<Answer> findByQuestionIdAndId(Long questionId, Long id);

    List<Answer> findByQuestionId(Long questionId);
}
