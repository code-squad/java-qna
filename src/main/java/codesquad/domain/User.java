package codesquad.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

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
        if (isMatchedPassword(newUser)) {
            this.name = newUser.name;
            this.email = newUser.email;
            if (!newPassword.isEmpty()) {
                this.password = newPassword;
            }
            return true;
        }
        return false;
    }

    public boolean isMatchedPassword(User newUser) {
        return this.password.equals(newUser.password);
    }

    public boolean isMatchedPassword(String newPassword) {
        if (newPassword == null) {
            return false;
        }

        return newPassword.equals(password);
    }

    public boolean isMatchedId(Long newId) {
        if (newId == null) {
            return false;
        }

        return newId.equals(id);
    }

    public boolean isMatchedUserId(User otherUser) {
        if (otherUser == null) {
            return false;
        }
       return this.equals(otherUser);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public boolean isMatchedUserId(Question editedQuestion) {
        if (editedQuestion == null) {
            return false;
        }
        return editedQuestion.isMatchedWriter(userId);
    }

    public boolean isMatchedUserId(Answer deletedAnswer) {
        if (deletedAnswer == null) {
            return false;
        }

        return deletedAnswer.isMatchedWriter(userId);
    }

    public boolean isMatchedUserId(String userId) {
        if (userId == null) {
            return false;
        }
        return this.userId.equals(userId);
    }


}
