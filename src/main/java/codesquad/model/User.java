package codesquad.model;

import codesquad.exceptions.PasswordMismatchException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Email;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

@Entity
public class User extends AbstractEntity {

    @Size(min = 3, max = 20)
    @Column(nullable = false, length = 32, unique = true)
    private String userId;

    @Size(min = 6, max = 20)
    @Column(nullable = false, length = 32)
    @JsonIgnore
    private String password;

    @Size(min = 3, max = 20)
    @Column(nullable = false, length = 64)
    @JsonIgnore
    private String name;

    @Email
    @Column(nullable = false)
    @JsonIgnore
    private String email;

    public void updateUserInfo(User newUser, String password) throws PasswordMismatchException {
        if (!passwordsMatch(password)) {
            throw new PasswordMismatchException("User.password.mismatch");
        }
        this.password = newUser.password;
        this.name = newUser.name;
        this.email = newUser.email;
        setDateLastModified(assignDateTime());
    }

    public boolean passwordsMatch(String password) {
        return this.password.equals(password);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userID) {
        this.userId = userID;
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
                "id=" + getId() +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", created=" + getDateCreated() +
                ", lastModified=" + getDateLastModified() +
                '}';
    }
}