package com.codessquad.qna;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class User {
    @NotNull
    @Size(min = 1, max = 10, message = "1자 이상 10자 미만으로 작성해야합니다.")
    private String id;

    @NotNull
    @Size(min = 1, max = 10)
    private String password;

    @NotNull
    @Size(min = 1, max = 10)
    private String name;

    @NotNull
    @Size(min = 1, max = 100)
    private String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "User{" +
                "userId='" + id + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
