package com.codessquad.qna.web.dto;

import com.codessquad.qna.domain.Users;
import java.time.LocalDateTime;

public class UsersListResponseDto {
  private Long Id;
  private String UserId;
  private String password;
  private String name;
  private String email;

  public UsersListResponseDto(Users entity) {
    this.Id = entity.getId();
    this.UserId = entity.getUserId();
    this.password = entity.getPassword();
    this.name = entity.getName();
    this.email = entity.getEmail();
  }
}