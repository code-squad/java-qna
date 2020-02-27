package com.codessquad.qna.web.dto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UsersUpdateRequestDto {
  private String userId;
  private String password;
  private String name;
  private String email;

  @Builder
  public UsersUpdateRequestDto(String userId, String password, String name, String email) {
    this.userId = userId;
    this.password = password;
    this.name = name;
    this.email = email;
  }
}
