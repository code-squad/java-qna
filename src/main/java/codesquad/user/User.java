package codesquad.user;

import codesquad.exception.ListFailedException;
import codesquad.exception.UpdatefailedException;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class User {
    //데이터베이스에 테이블 에는 데이터를 식별하는 키가 있어야한다. 각자 고유의 키
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 20, unique = true)
    private String userId;
    private String password;
    private String name;
    private String email;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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


    public void update(User newUser) {
        if (!newUser.matchPassword(this.password)) {
            throw new UpdatefailedException();
        }
        this.password = newUser.password;
        this.name = newUser.name;
        this.email = newUser.email;
    }

    public boolean equalsOfString(String o) {
        return Objects.equals(userId, o);
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

    public boolean matchPassword(String password) {
        return this.password.equals(password);
    }

    public boolean matchPassword(User otherUser) {
        return this.password.equals(otherUser.password);
    }

    public void matchId(long id) {
        if (this.id != id) {
            throw new ListFailedException();
        }

    }

    public boolean matchUserId(String writer) {
        return userId.equals(writer);
    }
}