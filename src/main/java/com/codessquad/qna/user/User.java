package com.codessquad.qna.user;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    @NotEmpty
    private String userId;
    @NotEmpty
    private String userPassword;
    @NotEmpty
    private String userName;
    @NotEmpty
    private String userEmail;

    public User() {}

    public User(String userId, String userPassword, String userName, String userEmail) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userEmail = userEmail;
    }

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

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public boolean isEqualsPassword(String password) {
        return this.userPassword.equals(password);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getId().equals(user.getId()) && getUserId().equals(user.getUserId()) && getUserPassword()
                .equals(user.getUserPassword()) && getUserName().equals(user.getUserName()) && getUserEmail()
                .equals(user.getUserEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUserId(), getUserPassword(), getUserName(), getUserEmail());
    }
}
