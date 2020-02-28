package com.codessquad.qna.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class User {
    @Id
    @GeneratedValue
    public Long id;

    @Column(nullable=false, length = 20, unique = true)
    public String userId;
    public String password;
    public String name;
    public String email;

    public Long getId() { return id; }
    public String getUserId() {
        return userId;
    }
    public String getPassword() { return password; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    public void setId(Long id) {
        this.id = id;
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

    public boolean matchPassword(String newPassword) {
        if (newPassword == null) { return false; }
        return newPassword.equals(password);
    }

    public boolean matchId(Long newId) {
        if (newId == null) { return false; }
        return newId.equals(id);
    }

    public void update (User newUser) {
        this.password = newUser.password;
        this.name = newUser.name;
        this.email = newUser.email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User {" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
