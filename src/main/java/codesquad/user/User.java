package codesquad.user;

import codesquad.qna.answers.Answer;
import codesquad.qna.questions.Question;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동으로 1씩 증가하며 아이디를 부여.
    private long id;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public boolean matchId(long id){
        return this.id == id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
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
        return id == user.id &&
                Objects.equals(userId, user.userId) &&
                Objects.equals(password, user.password) &&
                Objects.equals(name, user.name) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, password, name, email);
    }
}
