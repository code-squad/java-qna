package com.codessquad.qna.repository;

import com.codessquad.qna.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  @Query("SELECT u FROM User u WHERE u.name = ?1")
  User findUserByName(String name);

  @Query("SELECT u FROM User u WHERE u.userId = ?1")
  User findUserByUserId(String userId);


}
