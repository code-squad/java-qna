package codesquad.user;

import codesquad.qna.Question;

import javax.persistence.*;
import java.util.Objects;

@Entity         //데이터 베이스 맵핑할때 알아서 꺼내줌
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 20, unique = true)
    private String userId;

    @Column(nullable = false, length = 20)
    private String password;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 20)
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

    public void update(User updateUser) {
        this.password = updateUser.password;
        this.name = updateUser.name;
        this.email = updateUser.email;
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

    public boolean matchPassword(String newPassword) {
        if (newPassword == null) {
            return false;
        }
        return this.password.equals(newPassword);
    }

    public boolean matchId(Long newId) {
        if (newId == null) {
            return false;
        }
        return this.id == newId;
    }

    public boolean matchUser(User sessionedUser) {
        return this.id == sessionedUser.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getId() == user.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
