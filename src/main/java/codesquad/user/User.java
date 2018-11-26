package codesquad.user;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class User {
    @Id //기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY)    //db에 자동으로 새로 추가된 데이터의 userId 번호+1
    private long pId;

    @Column(nullable = false, length = 20, unique = true)
    private String userId;
    private String password;
    private String name;

    private String email;

    public long getPId() {
        return pId;
    }

    public void setPId(long pId) {
        this.pId = pId;
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

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    void update(User updatedUser) {
        if (!this.userId.equals(updatedUser.userId)) throw new IllegalArgumentException();
        this.password = updatedUser.password;
        this.name = updatedUser.name;
        this.email = updatedUser.email;
    }

    boolean matchPassword(String password) {
        return this.password.equals(password);
    }

    boolean matchPassword(User updatedUser) {
        return this.password.equals(updatedUser.password);
    }

    boolean matchPId(long pId) {
        return this.pId == pId;
    }

    public boolean matchWriter(String writer) {
        return this.userId.equals(writer);
    }

    public boolean matchUser(User other) {
        return this.userId == other.userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return pId == user.pId &&
                Objects.equals(userId, user.userId) &&
                Objects.equals(password, user.password) &&
                Objects.equals(name, user.name) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pId, userId, password, name, email);
    }
}
