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

    @Column(nullable = false, length = 20, unique = true)
    @NotEmpty
    private String userId;

    @NotEmpty
    private String userPassword;

    @NotEmpty
    @Column(unique = true)
    private String userName;

    @NotEmpty
    @Column(unique = true)
    private String userEmail;

    private String userProfileImage;

    public User() {}

    public User(String userId, String userPassword, String userName, String userEmail) {
        this(userId, userPassword, userName, userEmail, "../images/80-text.png");
    }

    public User(String userId, String userPassword, String userName, String userEmail, String userProfileImage) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userProfileImage = userProfileImage;
    }

    public Long getId() {
        return id;
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

    public String getUserProfileImage() {
        return userProfileImage;
    }

    public void setUserProfileImage(String userProfileImage) {
        this.userProfileImage = userProfileImage;
    }

    public boolean isUserPasswordNotEquals(String password) {
        return !this.userPassword.equals(password);
    }

    public void update(User updateUser, String newPassword) {
        if (!"".equals(newPassword)) {
            this.userPassword = newPassword;
        }
        this.userName = updateUser.userName;
        this.userEmail = updateUser.userEmail;
        this.userProfileImage = updateUser.userProfileImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof User)) { return false; }
        User user = (User) o;
        return this.userEmail.equals(user.userEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userEmail);
    }
}
