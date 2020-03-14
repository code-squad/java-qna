package com.codessquad.qna.user;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Data
@NotNull
public class User implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String userId;
  @Column(nullable = false)
  private String password;
  @Column(nullable = false)
  private String name;
  @Column(nullable = false)
  private String email;

  @Transient
  private String oldPassword;

  public User(Long id, String userId, String password, String name, String email) {
    this.id = id;
    this.userId = userId;
    this.password = password;
    this.name = name;
    this.email = email;
  }

  public void update(User newUser) {
    this.password = newUser.password;
    this.name = newUser.name;
    this.email = newUser.email;
  }

  public boolean validatePassword(String password) {
    return this.password.equals(password);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof User)) return false;
    User user = (User) o;
    return Objects.equals(getId(), user.getId()) &&
           Objects.equals(getUserId(), user.getUserId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getUserId());
  }
}
