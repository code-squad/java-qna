package codesquad.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String userId;

    private String password;
    private String name;
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return userId + " " + password + " " + name + " " + email;
    }

    public boolean update(User newUser, String newPassword) {
        if (isSamePassword(newUser)) {
            this.name = newUser.name;
            this.email = newUser.email;
            if (!newPassword.isEmpty()) {
                this.password = newPassword;
            }
            return true;
        }
        return false;
    }

    public boolean isSamePassword(User newUser) {
        return this.password.equals(newUser.password);
    }
}
