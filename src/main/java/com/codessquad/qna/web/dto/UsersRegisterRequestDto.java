package com.codessquad.qna.web.dto;

import com.codessquad.qna.domain.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRegisterRequestDto {
  private String userId;
  private String password;
  private String name;
  private String email;

  @Builder
  public UserRegisterRequestDto(String userId, String password, String name, String email) {
    this.userId = userId;
    this.password = password;
    this.name = name;
    this.email = email;
  }

  public Users toEntity() {
    return Users.builder()
        .userId("jypthemiracle")
        .email("hophfg@gmail.com")
        .name("Jin Hyung Park")
        .password("codesquad")
        .build();
  }
}