package com.codessquad.qna.user;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NotNull
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 20)
  private String userId;
  private String password;
  private String name;
  private String email;

  @Transient
  private String oldPassword;

  public void update(User newUser) {
    this.password = newUser.password;
    this.name = newUser.name;
    this.email = newUser.email;
  }

  public boolean validatePassword(String password) {
    return this.password.equals(password);
  }
}
