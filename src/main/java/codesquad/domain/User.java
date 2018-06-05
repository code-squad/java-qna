package codesquad.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class User extends AbstractEntiry {
    private static final Logger log = LoggerFactory.getLogger(User.class);

    @Column(nullable = false)
    @JsonProperty
    private String userId;

    @JsonIgnore
    @Column(nullable = false, length = 30)
    private String password;

    @JsonProperty
    private String name;

    @JsonProperty
    private String email;

    public User() {
    }

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

    public boolean update(User updateUser, String newPassword) {
        if (isMatchedPassword(updateUser)) {
            this.name = updateUser.name;
            this.email = updateUser.email;
            if (!newPassword.isEmpty() && newPassword.length() > 3) {
                this.password = newPassword;
            }
            return true;
        }
        return false;
    }

    public boolean isMatchedPassword(User updateUser) {
        return isMatchedPassword(updateUser.password);
    }

    public boolean isMatchedPassword(String password) {
        if (password.isEmpty()) {
            log.debug("password is : {}", password);
            throw new NullPointerException("password.null");
        }
        if (password.length() < 3) {
            log.debug("password length is : {}", password.length());
            throw new IllegalStateException("password.length");
        }

        return password.equals(this.password);
    }

    public boolean isMatchedUser(User otherUser) {
        if (otherUser == null) {
            return false;
        }
        if (!this.equals(otherUser)) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "Id='" + getId() + '\'' +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
