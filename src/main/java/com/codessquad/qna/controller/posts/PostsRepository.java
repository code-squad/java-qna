package com.codessquad.qna.controller.posts;

import com.codessquad.qna.domain.Posts;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PostsRepository extends CrudRepository<Posts, Long> {
  @Query("SELECT p FROM Posts p where p.deleteStatus = false ORDER BY p.Id DESC")
  List<Posts> findAllDesc();
  @Query("SELECT p FROM Posts p where p.Id = :Id AND p.deleteStatus = false")
  Posts findByPostId(@Param("Id") Long Id);
}