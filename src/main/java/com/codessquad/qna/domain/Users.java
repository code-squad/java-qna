package com.codessquad.qna.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 20)
  private String userId;
  private String password;
  private String name;
  private String email;

  public void update(User newUser) {
    this.name = newUser.name;
    this.password = newUser.password;
    this.email = newUser.email;
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", userId='" + userId + '\'' +
        ", password='" + password + '\'' +
        ", name='" + name + '\'' +
        ", email='" + email + '\'' +
        '}';
  }
}