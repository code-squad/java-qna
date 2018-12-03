package codesquad.user;

import codesquad.utils.Result;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Column(nullable = false, unique = true, length = 20)
    private String userId;

    @NotBlank
    @Column(nullable = false, length = 40)
    private String password;

    @NotBlank
    @Column(nullable = false, length = 30)
    private String name;

    @NotBlank
    @Column(nullable = false, length = 30)
    private String email;

    public User() {
    }

    public User(long id, String userId, String password, String name, String email) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

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

    public boolean matchPassword(String password) {
        return this.password.equals(password);
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

    public Result update(User updatedUser, User sessionedUser) {
        if(!this.equals(sessionedUser)) {
            return Result.fail("다른 유저의 정보에 접근할 수 없습니다");
        }
        this.password = updatedUser.password;
        this.name = updatedUser.name;
        this.email = updatedUser.email;
        return Result.ok();
    }

    public Result isSameUser(long id) {
        if (this.id != id) {
            return Result.fail("다른 유저의 정보에 접근할 수 없습니다");
        }
        return Result.ok();
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
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
