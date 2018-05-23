package codesquad.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class User extends AbstractEntity {
    @Column(nullable = false, length = 20, unique=true)
    @JsonProperty
    private String userId;

    @JsonIgnore
    private String password;

    @JsonProperty
    private String name;

    @JsonProperty
    private String email;

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

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean matchPassword(String newPassword) {
        if (newPassword == null)
            return false;
        return newPassword.equals(password);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User update(User loginUser, String password, String name, String email) {
        if (!isSameUser(loginUser))
            throw new IllegalStateException("유저가 다릅니다.");
        this.password = password;
        this.name = name;
        this.email = email;
        return this;
    }

    public boolean isSameUser(User loginUser) {
        return this.userId.equals(loginUser.getUserId());
    }

    public boolean matchId(Long newId) {
        if (newId == null)
            return false;
        return newId.equals(getId());
    }

    @Override
    public String toString() {
        return "User{" + super.toString() +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
