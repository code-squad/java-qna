package com.codessquad.qna.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(length = 20, unique = true)
    private String userId;

    @NotBlank
    @Column(length = 20)
    private String password;

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @Transient
    private String updatedPassword;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getUpdatedPassword() {
        return updatedPassword;
    }

    public void setUpdatedPassword(String updatedPassword) {
        this.updatedPassword = updatedPassword;
    }

    public void update(User updatedUser) {
        password = checkPassword(updatedUser);
        name = updatedUser.name;
        email = updatedUser.email;
    }

    private String checkPassword(User updatedUser) {
        if(updatedUser.updatedPassword.length()>0){
            return updatedUser.updatedPassword;
        }
        return updatedUser.password;
    }

    public boolean matchPassword(User inputUser) {
        return password.equals(inputUser.password);
    }

    public boolean matchPassword(String inputPassword) {
        return password.equals(inputPassword);
    }

    public boolean matchId(Long inputId) {
        return id.equals(inputId);
    }

    public boolean matchName(String writer) {
        return name.equals(writer);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
