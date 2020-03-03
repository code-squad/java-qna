package com.codesquad.qna.web;

public class User {
    private String userId;
    private String password;
    private String userName;
    private String email;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "userId : " + userId + ", userName : " + userName + ", Email : " + email;
    }

}
