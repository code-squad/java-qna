package codesquad.user;

import codesquad.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class User extends AbstractEntity {
    @JsonProperty
    @Column(nullable = false, length = 20, unique = true)
    private String userId;

    @JsonIgnore
    @Column(nullable = false, length = 20)
    private String password;

    @JsonProperty
    @Column(nullable = false, length = 20)
    private String name;

    @JsonProperty
    @Column(nullable = false, length = 100)
    private String email;

    public User() {
    }

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
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

    public boolean update(User updateUser, User loginUser) {
        if (loginUser.matchPassword(password)) {
            this.password = updateUser.password;
            this.name = updateUser.name;
            this.email = updateUser.email;
            return true;
        }
        return false;
    }

    public boolean matchPassword(String password) {
        return this.password.equals(password);
    }

    public boolean matchId(Long id) {
        return this.getId().equals(id);
    }

    @Override
    public String toString() {
        return "User{" +
                super.toString() +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
