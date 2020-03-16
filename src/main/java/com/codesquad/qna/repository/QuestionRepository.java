package com.codesquad.qna.repository;

import com.codesquad.qna.domain.Question;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<Question, Long> {

}
