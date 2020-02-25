package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface PostsRepository extends CrudRepository<User, Long> {
}
