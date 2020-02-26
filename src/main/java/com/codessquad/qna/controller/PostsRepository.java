package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Posts;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PostsRepository extends CrudRepository<Posts, Long> {
  @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
  List<Posts> findAllDesc();
}