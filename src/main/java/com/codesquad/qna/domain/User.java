package com.codesquad.qna.domain;

import com.codesquad.qna.advice.exception.ForbiddenException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(length = 20, unique = true)
    private String userId;

    @NotEmpty
    private String password;

    @NotEmpty
    private String name;

    @NotEmpty
    private String email;

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

    public void update(User updateUser) {
        this.password = updateUser.password;
        this.name = updateUser.name;
        this.email = updateUser.email;
    }

    public boolean isPasswordEquals(String password) {
        return this.password.equals(password);
    }

    public boolean isIdEquals(Long id) {
        return this.id.equals(id);
    }

    public boolean isIdEquals(Question question) {
        Long questionId = question.getWriter().id;
        return isIdEquals(questionId);
    }

    public void hasPermission(Long id) {
        if (!isIdEquals(id)) {
            throw ForbiddenException.noPermission();
        }
    }

    public void hasPermission(Question question) {
        Long writerId = question.getWriter().id;
        hasPermission(writerId);
    }

    public void hasPermission(Answer answer) {
        Long writerId = answer.getWriter().id;
        hasPermission(writerId);
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
