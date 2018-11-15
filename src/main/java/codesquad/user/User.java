package codesquad.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity  // 알아서 데이터베이스 넣거나 꺼낼때 Mapping해줌
public class User {
    @Id  // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // +1씩 자동증가
    private long id;

    private int index;
    private String userId;
    private String password;
    private String name;
    private String email;

    public User() {
    }

    public User(int index, String userId, String password, String name, String email) {
        this.index = index;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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

    public boolean isUserId(String userId) {
        return this.userId.equals(userId);
    }

    @Override
    public String toString() {
        return "User{" +
                "index=" + index +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
