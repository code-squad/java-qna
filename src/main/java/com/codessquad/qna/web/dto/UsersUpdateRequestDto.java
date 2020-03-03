package com.codessquad.qna.web.dto;

public class UsersUpdateRequestDto {
  private String userId;
  private String password;
  private String name;
  private String email;

  public UsersUpdateRequestDto(String userId, String password, String name, String email) {
    this.userId = userId;
    this.password = password;
    this.name = name;
    this.email = email;
  }

  public UsersUpdateRequestDto() {
  }

  public static UsersUpdateRequestDtoBuilder builder() {
    return new UsersUpdateRequestDtoBuilder();
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

  public static class UsersUpdateRequestDtoBuilder {

    private String userId;
    private String password;
    private String name;
    private String email;

    UsersUpdateRequestDtoBuilder() {
    }

    public UsersUpdateRequestDto.UsersUpdateRequestDtoBuilder userId(String userId) {
      this.userId = userId;
      return this;
    }

    public UsersUpdateRequestDto.UsersUpdateRequestDtoBuilder password(String password) {
      this.password = password;
      return this;
    }

    public UsersUpdateRequestDto.UsersUpdateRequestDtoBuilder name(String name) {
      this.name = name;
      return this;
    }

    public UsersUpdateRequestDto.UsersUpdateRequestDtoBuilder email(String email) {
      this.email = email;
      return this;
    }

    public UsersUpdateRequestDto build() {
      return new UsersUpdateRequestDto(userId, password, name, email);
    }

    public String toString() {
      return "UsersUpdateRequestDto.UsersUpdateRequestDtoBuilder(userId=" + this.userId
          + ", password=" + this.password + ", name=" + this.name + ", email=" + this.email + ")";
    }
  }
}
