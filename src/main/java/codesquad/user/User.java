package codesquad.user;

import codesquad.AbstractEntity;
import codesquad.qna.answers.Answer;
import codesquad.qna.questions.Question;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class User extends AbstractEntity {
    @Column(unique = true, nullable = false, length = 20)
    private String userId;

    @Column(length = 20)
    private String password;

    @Column(length = 10)
    private String name;

    @Column(length = 40)
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

    public boolean isUser(String userId){
        return userId.equals(this.userId);
    }

    public void update(User modifiedUser) {
        if(!modifiedUser.isUser(this.userId) || modifiedUser.matchPassword(this.password))
            throw new IllegalStateException("permission denied.");

        this.name = modifiedUser.name;
        this.email = modifiedUser.email;
    }

    public boolean matchPassword(String password) {
        return this.password.equals(password);
    }

    public boolean matchPassword(User user) {
        return user.matchPassword(this.password);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
