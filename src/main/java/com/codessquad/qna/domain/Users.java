package com.codessquad.qna.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Users {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long Id;

  private String userId;

  @Column(nullable = false, length = 20)
  private String password;
  private String name;
  private String email;

  @Builder
  public Users(String userId, String password, String name, String email) {
    this.userId = userId;
    this.password = password;
    this.name = name;
    this.email = email;
  }

  public void update(String userId, String name, String password, String email) {
    this.userId = userId;
    this.password = password;
    this.name = name;
    this.email = email;
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + Id +
        ", userId='" + userId + '\'' +
        ", password='" + password + '\'' +
        ", name='" + name + '\'' +
        ", email='" + email + '\'' +
        '}';
  }
}