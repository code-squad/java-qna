package codesquad.user;

import codesquad.question.Question;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    //TODO : 이미 가입 된 유저아이디를 기입하고 회원가입 시에 재입력하도록(현재는 SQL 에러페이지)
    @Column(nullable = false, length = 20, unique = true)
    private String userId;
    private String password;
    private String name;
    private String email;

    void update(User updated) {
        this.name = updated.name;
        this.password = updated.password;
        this.email = updated.email;
    }

    //시간관계상 널체크무시
    //todo null체크
    public boolean matchPassword(String password) {
        if(password == null) {
            return false;
        }

        return this.password.equals(password);
    }

    public boolean matchPassword(User other) {
//        if(other == null) {
//            return false;
//        }

        return this.password.equals(other.password);
    }

//    todo : get메소드 사용안하기 가능?
    public boolean isMatchName(String name) {
        return this.name.equals(name);
    }

    public boolean isMatchId(long id) {
        return this.id == id;
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
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
