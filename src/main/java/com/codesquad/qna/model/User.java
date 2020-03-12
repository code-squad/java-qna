package com.codesquad.qna.model;

import com.codesquad.qna.model.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Entity
public class User extends BaseEntity {
    @Id
    private String userId;

    @Column(nullable = false)
    @NotEmpty
    private String password;

    @Column(nullable = false)
    @NotEmpty
    private String name;

    private String email;

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

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void update(User updateUser) {
        this.name = updateUser.name;
        this.email = updateUser.email;
    }

    public boolean matchPassword(User sessionedUser) {
        return this.password.equals(sessionedUser.password);
    }

    public boolean matchId(String userId) {
        return this.userId.equals(userId);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", " + super.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, password);
    }
}
