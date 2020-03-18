package com.codessquad.qna.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class User extends AbstractEntity {
    @Column(nullable = false, length = 20)
    @JsonProperty
    private String userId;
    @JsonIgnore
    private String password;
    @JsonProperty
    private String name;
    @JsonProperty
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
        this.email = email; }

    public void update(UserUpdateDTO userUpdateDTO) {
        this.name = userUpdateDTO.getName();
        this.email = userUpdateDTO.getEmail();
        this.password = userUpdateDTO.getPassword();
    }

    public boolean notMatchId(Long id) {
        return !id.equals(this.getId());
    }

    public boolean notMatchPassword(UserUpdateDTO userUpdateDTO) {
        return !userUpdateDTO.getPassword().equals(this.password);
    }

    public boolean notMatchPassword(String password) {
        return !password.equals(this.password);
    }

    public boolean notMatchWriter(String writer) {
        return !writer.equals(name);
    }
}
