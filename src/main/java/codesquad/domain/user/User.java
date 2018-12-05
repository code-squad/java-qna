package codesquad.domain.user;

import codesquad.domain.qna.Question;

import javax.persistence.*;
import java.util.Objects;

@Entity // 데이터베이스와 연동이라는 것을 명시 //
public class User {
    public static final int USERID_LENGTH = 20;
    public static final int PASSWORD_LENGTH = 20;
    public static final int NAME_LENGTH = 20;

    @Id // 기본키 설정 //
    @Column(name = "USER_ID_FK")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 데이터베이스에서 자동으로 숫자를 증가해서 관리 //
    private Long id;

    @Column(nullable = false, length = USERID_LENGTH, updatable = false, unique = true) // Not null 지정 //
    private String userId;

    @Column(nullable = false, length = PASSWORD_LENGTH) // Not null 지정 //
    private String password;

    @Column(nullable = false, length = NAME_LENGTH) // Not null 지정 //
    private String name;

    @Column(length = 20) // Not null 지정 //
    private String email;

    public User() {

    }

    public User(Long id, String userId, String password, String name, String email) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public void update(User user) {
        this.name = user.name;
        this.email = user.email;
    }

    public boolean isValidPassword(String password) {
        return this.password.equals(password);
    }

    public boolean identification(Long id) {
        return this.id.longValue() == id.longValue();
    }

    public boolean isDuplicationUserId(String userId) {
        return this.userId.equals(userId);
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
        return Objects.equals(id, user.id) &&
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
