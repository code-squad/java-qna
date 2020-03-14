package com.codessquad.qna.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Users {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long Id;

  private String userId;

  @Column(nullable = false, length = 20)
  private String password;
  private String name;
  private String email;

  public Users(String userId, String password, String name, String email) {
    this.userId = userId;
    this.password = password;
    this.name = name;
    this.email = email;
  }

  public Users() {
  }

  public static UsersBuilder builder() {
    return new UsersBuilder();
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

  public static class UsersBuilder {

    private String userId;
    private String password;
    private String name;
    private String email;

    UsersBuilder() {
    }

    public Users.UsersBuilder userId(String userId) {
      this.userId = userId;
      return this;
    }

    public Users.UsersBuilder password(String password) {
      this.password = password;
      return this;
    }

    public Users.UsersBuilder name(String name) {
      this.name = name;
      return this;
    }

    public Users.UsersBuilder email(String email) {
      this.email = email;
      return this;
    }

    public Users build() {
      return new Users(userId, password, name, email);
    }

    public String toString() {
      return "Users.UsersBuilder(userId=" + this.userId + ", password=" + this.password + ", name="
          + this.name + ", email=" + this.email + ")";
    }
  }

  public void setId(Long id) {
    Id = id;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }
}