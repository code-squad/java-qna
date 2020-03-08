package com.codessquad.qna.web.question;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends CrudRepository<Answer, Long> {
    List<Answer> findByQuestionIdAndIsDeletedFalse(Long questionId);

    Optional<Answer> findByQuestionIdAndId(Long questionId, Long answerId);

    @Modifying
    @Query("update Answer a set a.isDeleted = true where a.question = :question")
    void deleteAnswersInQuestion(@Param("question") Question question);
}
