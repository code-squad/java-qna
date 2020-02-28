package com.codessquad.qna.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.NoArgsConstructor;

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

  public boolean matchId(Long newId) {
    if (newId == null) {
      return false;
    }
    return newId.equals(Id);
  }

  public boolean matchPassword(String newPassword) {
    if (newPassword == null) {
      return false;
    }
    return newPassword.equals(password);
  }

  public Long getId() {
    return this.Id;
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