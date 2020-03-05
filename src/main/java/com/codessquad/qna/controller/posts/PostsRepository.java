package com.codessquad.qna.controller.posts;

import com.codessquad.qna.domain.Posts;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PostsRepository extends CrudRepository<Posts, Long> {
  @Query("SELECT p FROM Posts p ORDER BY p.Id DESC")
  List<Posts> findAllDesc();
  @Query("SELECT p FROM Posts p where p.Id = :Id")
  Posts findByPostId(@Param("Id") Long Id);
}