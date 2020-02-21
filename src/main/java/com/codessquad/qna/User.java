package com.codessquad.qna;

public class User {
    private String userId;
    private String password;
    private String name;
    private String email;
    private int index;

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

    public void setIndex(int index) {
        this.index = index;
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

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return "index: " + index + "userId: " + userId + " password: " + password + " name: " + name + " email: " + email;
    }
}
