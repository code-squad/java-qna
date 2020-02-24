package com.codesquad.qna.domain;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 20)
    private String userId;
    @Column(nullable = false)
    private String password;
//    private String newPassword;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    private String address;
    private String phoneNumber;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void update(User user, String newPassword) {
        // newPassword 가 null이 아닌경우에만 비밀번호 변경
        if (!newPassword.isEmpty())
            this.password = newPassword;
        this.name = user.getName();
        this.address = user.getAddress();
        this.phoneNumber = user.getPhoneNumber();
        this.email = user.getEmail();
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}
