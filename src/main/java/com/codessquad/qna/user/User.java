package com.codessquad.qna.user;

import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Entity;

@Entity
public class User {
  private static int SEQ_NUM;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int index;

  @Column(nullable = false, length = 20)
  private String userId;
  private String passwd;
  private String name;
  private String email;

  public User() {
    this.index = ++SEQ_NUM;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

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

}
