package com.codessquad.qna.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
public class User extends AbstractEntity {
    @NotBlank
    @Column(length = 20, unique = true)
    @JsonProperty
    private String userId;

    @NotBlank
    @Column(length = 20)
    @JsonIgnore
    private String password;

    @NotBlank
    @JsonProperty
    private String name;

    @NotBlank
    @JsonProperty
    private String email;

    @Transient
    private String updatedPassword;

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
        return getId().equals(inputId);
    }

    public boolean matchName(String writer) {
        return name.equals(writer);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId.equals(user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
