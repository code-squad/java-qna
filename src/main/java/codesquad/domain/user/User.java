package codesquad.domain.user;

import codesquad.domain.AbstractEntity;
import codesquad.exception.ListFailedException;
import codesquad.exception.UpdatefailedException;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class User extends AbstractEntity {
    //데이터베이스에 테이블 에는 데이터를 식별하는 키가 있어야한다. 각자 고유의 키

    @Column(nullable = false, length = 20, unique = true)
    private String userId;

    @Column(nullable = false, length = 20)
    private String password;

    @Column(nullable = false, length = 15)
    private String name;

    @Column
    private String email;

    public User() {
    }

    public User(int id, String userId, String password, String name, String email) {
        super.setId(id);
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return super.getId();
    }

    public void setId(long id) {
        super.setId(id);
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
            throw new UpdatefailedException("비밀번호가 다릅니다. 다시 입력해주세요");
        }
        this.password = newUser.password;
        this.name = newUser.name;
        this.email = newUser.email;
    }

    public boolean equalsOfString(String o) {
        return Objects.equals(userId, o);
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

    public boolean matchPassword(String password) {
        return this.password.equals(password);
    }

    public boolean matchPassword(User otherUser) {
        return this.password.equals(otherUser.password);
    }

    public void matchId(long id) {
        if (getId() != id) {
            throw new ListFailedException("본인의 아이디를 선택해 주세요");
        }

    }

    public boolean matchUserId(String writer) {
        return userId.equals(writer);
    }
}