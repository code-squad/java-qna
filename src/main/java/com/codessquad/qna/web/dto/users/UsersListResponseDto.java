package com.codessquad.qna.web.dto.users;

import com.codessquad.qna.domain.Users;

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

  public Long getId() {
    return this.Id;
  }

  public String getUserId() {
    return this.UserId;
  }

  public String getPassword() {
    return this.password;
  }

  public String getName() {
    return this.name;
  }

  public String getEmail() {
    return this.email;
  }
}