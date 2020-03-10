package com.codessquad.qna.domain;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String accountId;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String nickName;
    @Column(nullable = false)
    private String email;
    @Column
    private String introduction;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String userId) {
        this.accountId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String name) {
        this.nickName = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public User merge(User newUser) {
        setNickName(newUser.nickName);
        setIntroduction(newUser.introduction);
        return this;
    }

    public boolean verify(User target) {
        return password.equals(target.password);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", accountId='" + accountId + '\'' +
                ", password='" + password + '\'' +
                ", nickName='" + nickName + '\'' +
                ", email='" + email + '\'' +
                ", introduction='" + introduction + '\'' +
                '}';
    }
}
