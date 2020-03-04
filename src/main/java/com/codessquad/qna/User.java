package com.codessquad.qna;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long givenNumber;

    @Column(nullable = false, length = 15, unique = true)
    private String userId;
    private String password;
    private String name;
    private String email;

    public Long getGivenNumber() {
        return givenNumber;
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

    @Override
    public String toString() {
        return "User [" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ']';
    }

    public void update(User newUser) {
        this.name =newUser.name;
        this.password = newUser.password;
        this.email = newUser.email;
    }
}
