package com.codessquad.qna.web.dto;

import com.codessquad.qna.domain.Users;

//PostsResponseDto는 Entity의 필드 중 일부만을 사용한다. 생성자로 Entity를 받아 필드에 값을 넣는다.

public class UsersResponseDto {

  private Long id;
  private String userId;
  private String password;
  private String name;
  private String email;

  public UsersResponseDto(Users entity) {
    this.id = entity.getId();
    this.userId = entity.getUserId();
    this.password = entity.getPassword();
    this.name = entity.getName();
    this.email = entity.getEmail();
  }

  public UsersResponseDto() {
  }

  public Long getId() {
    return this.id;
  }

  public String getUserId() {
    return this.userId;
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