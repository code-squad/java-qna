package com.codesquad.qna.domain;

import org.springframework.data.repository.CrudRepository;

public interface QuestionRepostory extends CrudRepository<Question, Long> {
}
