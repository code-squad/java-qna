package codesquad.user;

import codesquad.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class User extends AbstractEntity {
    //TODO : 이미 가입 된 유저아이디를 기입하면 재입력하도록 유도
    @Column(nullable = false, length = 20, unique = true)
    private String userId;

    @JsonIgnore
    @Column(nullable = false, length = 20)
    private String password;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 50)
    private String email;


    public User() { }

    public User(long id, String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    //TODO 컨트롤러의 로직을 최대한 도메인으로

    void update(User updated) {
        if(isMatchId(updated.getId())) {
            this.name = updated.name;
            this.password = updated.password;
            this.email = updated.email;
        }
    }

    public boolean isMatchId(long id) {
        return this.getId() == id;
    }

    public boolean isMatchPassword(String password) {
        return this.password.equals(password);
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
                "id=" + getId() +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
