package com.codessquad.qna.answer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AnswerRepository extends CrudRepository<Answer, Long> {
}
