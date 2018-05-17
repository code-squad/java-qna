package codesquad.web.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id; // Long -> null 체크를 가능하게 함으로써 유저정보 유무를 확인

    @Column(nullable = false, length = 20)
    private String userId;
    private String password;
    private String name;
    private String email;


    public User() { }

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {

        return id;
    }

    public boolean update(String beforePassword, User updateUser) {
        if (!this.isMatch(beforePassword)) return false;
        if (nothingToChange(updateUser)) return false;
        this.updateUserInfo(updateUser);
        return true;
    }

    private boolean nothingToChange(User updateUser) {
        return password.equals(updateUser.password)
                && name.equals(updateUser.name)
                && email.equals(updateUser.email);
    }

    boolean isMatch(String beforePassword) {
        return password.equals(beforePassword);
    }

    private void updateUserInfo(User updateUser) {
        password = updateUser.password;
        name = updateUser.name;
        email = updateUser.email;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String userId;
        private String password;
        private String name;
        private String email;

        Builder() { }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    private User (Builder builder) {
        userId = builder.userId;
        password = builder.password;
        name = builder.name;
        email = builder.email;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) &&
                Objects.equals(password, user.password) &&
                Objects.equals(name, user.name) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userId, password, name, email);
    }


}
