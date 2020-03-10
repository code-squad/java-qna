package com.codessquad.qna.controller.answers;

import com.codessquad.qna.domain.Answers;
import com.codessquad.qna.domain.Users;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswersRepository extends JpaRepository<Answers, Long> {
  @Query("SELECT p FROM Answers p where p.deleteStatus = false ORDER BY p.Id DESC")
  List<Answers> findAllDesc();

  @Query("SELECT p FROM Answers p where p.Id = :Id AND p.deleteStatus = false")
  Answers findById(@Param("Id") String Id);

//  List<Answers> findByPostId(Long postId);
}