package com.codessquad.qna.user;

import javax.persistence.*;

@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Transient
  private String oldPassword;

  @Column(nullable = false, length = 20)
  private String userId;
  private String password;
  private String name;
  private String email;

  public String getOldPassword() {
    return oldPassword;
  }

  public void setOldPassword(String oldPassword) {
    this.oldPassword = oldPassword;
  }

  public long getId() {
    return id;
  }

  public void setId(long index) {
    this.id = index;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUserId() {
    return userId;
  }

  public String getPassword() {
    return password;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public void update(User newUser) {
    this.password = newUser.password;
    this.name = newUser.name;
    this.email = newUser.email;
  }
}
