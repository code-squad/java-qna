package com.codessquad.qna.web.dto.users;

import com.codessquad.qna.domain.Users;

public class UsersRegisterRequestDto {
  private String userId;
  private String password;
  private String name;
  private String email;

  public UsersRegisterRequestDto(String userId, String password, String name, String email) {
    this.userId = userId;
    this.password = password;
    this.name = name;
    this.email = email;
  }

  public UsersRegisterRequestDto() {
  }

  public static UsersRegisterRequestDtoBuilder builder() {
    return new UsersRegisterRequestDtoBuilder();
  }

  public Users toEntity() {
    return Users.builder()
        .userId(userId)
        .email(email)
        .name(name)
        .password(password)
        .build();
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

  public static class UsersRegisterRequestDtoBuilder {

    private String userId;
    private String password;
    private String name;
    private String email;

    UsersRegisterRequestDtoBuilder() {
    }

    public UsersRegisterRequestDto.UsersRegisterRequestDtoBuilder userId(String userId) {
      this.userId = userId;
      return this;
    }

    public UsersRegisterRequestDto.UsersRegisterRequestDtoBuilder password(String password) {
      this.password = password;
      return this;
    }

    public UsersRegisterRequestDto.UsersRegisterRequestDtoBuilder name(String name) {
      this.name = name;
      return this;
    }

    public UsersRegisterRequestDto.UsersRegisterRequestDtoBuilder email(String email) {
      this.email = email;
      return this;
    }

    public UsersRegisterRequestDto build() {
      return new UsersRegisterRequestDto(userId, password, name, email);
    }

    public String toString() {
      return "UsersRegisterRequestDto.UsersRegisterRequestDtoBuilder(userId=" + this.userId
          + ", password=" + this.password + ", name=" + this.name + ", email=" + this.email + ")";
    }
  }
}