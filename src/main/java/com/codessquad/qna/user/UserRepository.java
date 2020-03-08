package com.codessquad.qna.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
  @Transactional(readOnly = true)
  Optional<User> findByUserId(String userId);
}

