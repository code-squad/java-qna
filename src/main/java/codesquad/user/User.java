package codesquad.user;

import codesquad.AbstractEntity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class User extends AbstractEntity {

    @Column(nullable = false, length = 20, unique = true)
    private String userId;
    private String password;
    private String name;

    private String email;

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

    void update(User updatedUser) {
        if (!this.userId.equals(updatedUser.userId)) throw new IllegalArgumentException();
        this.password = updatedUser.password;
        this.name = updatedUser.name;
        this.email = updatedUser.email;
    }

    boolean matchPassword(String password) {
        return this.password.equals(password);
    }

    boolean matchPassword(User updatedUser) {
        return this.password.equals(updatedUser.password);
    }

    boolean matchPId(long pId) {
        return getPId() == pId;
    }

    public boolean matchWriter(String writer) {
        return this.userId.equals(writer);
    }

    public boolean matchUser(User other) {
        return this.userId.equals(other.userId);
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
}
