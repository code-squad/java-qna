package com.codesquad.qna.domain;

import javax.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=10, unique=true)
    private String userId;
    private String password;

    @Column
    private String userName;
    private String email;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() { return id; }

    public void update(User updatedUser) {
        this.password = updatedUser.password;
        this.userName = updatedUser.userName;
        this.email = updatedUser.email;
    }

    public boolean isCorrectPassword(String confirmPassword) {
        if (confirmPassword == null) {
            return false;
        }

        return password.equals(confirmPassword);
    }

    @Override
    public String toString() {
        return "userId : " + userId + ", userName : " + userName + ", password : " + password + ", Email : " + email + ", ID : " + id;
    }

}
