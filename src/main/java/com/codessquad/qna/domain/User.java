package com.codessquad.qna.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 25, unique = true)
  private String userId;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String email;

  public void update(User updateUser) {
    name = updateUser.getName();
    email = updateUser.getEmail();
    password = updateUser.getPassword();
  }

  public boolean isSameUser(User sessionUser) {
    return matchId(sessionUser.getId());
  }

  public boolean matchId(Long id) {
    if (id == null) {
      return false;
    }
    return this.id.equals(id);
  }

  public boolean matchPassword(String password) {
    if (password == null) {
      return false;
    }
    return this.password.equals(password);
  }

  public Long getId() {
    return id;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

}
