package com.codessquad.qna;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    @NotEmpty
    private String userId;

    @NotEmpty
    private String password;

    @NotEmpty
    private String name;

    @NotEmpty
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

    public Long getId() {
        return id;
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

    public boolean isIdEquals(Long inputId) {
        if (inputId == null) {
            return false;
        }
        return inputId.equals(id);
    }

    public boolean isPasswordEquals(String inputPassword) {
        if (inputPassword == null) {
            return false;
        }
        return inputPassword.equals(password);
    }

    public void update(String name, String email) {
        this.name = name;
        this.email = email;
    }

    @Override
    public String toString() {
        return "index: " + id + "  userId: " + userId + " password: " + password + " name: " + name + " email: " + email;
    }
}
