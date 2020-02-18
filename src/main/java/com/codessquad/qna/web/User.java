package com.codessquad.qna.web;

public class User {
  private String userId;
  private String passwd;
  private String name;
  private String email;

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public void setPasswd(String passwd) {
    this.passwd = passwd;
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

  public String getPasswd() {
    return passwd;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public String toString() {
    return "User{" + "userId='" + userId + '\'' + ", passwd='" + passwd + '\''
        + ", name='" + name + '\'' + ", email='" + email + '\'' + '}';
  }
}
