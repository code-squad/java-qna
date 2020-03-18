package com.codessquad.qna.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class User extends AbstractEntity {
    @Column(nullable = false, length = 20)
    private String userId;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void update(User user) {
        if (!isValid(user)) {
            throw new RuntimeException("InvalidUserUpdate");
        }

        this.name = user.name;
        this.email = user.email;
        this.password = user.password;
    }

    private boolean isValid(User user) {
        return user.name != null && user.email != null && user.password != null;
    }

    public boolean matchPassword(String newPassword) {
        if (newPassword == null) {
            return false;
        }

        return newPassword.equals(password);
    }

    @Override
    public String toString() {
        return "User{" +
                super.toString() +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
