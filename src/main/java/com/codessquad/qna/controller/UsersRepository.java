package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Users;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<Users, Long> {
  @Query("SELECT p FROM Users p ORDER BY p.Id DESC")
  List<Users> findAllDesc();
}