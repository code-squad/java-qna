package com.codesquad.qna.domain;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface QuestionRepostory extends CrudRepository<Question, Long> {
    List<Question> findByDeletedFalse();
}
