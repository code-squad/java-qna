package codesquad.user;

import javax.persistence.*;

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

    public void update(User newUser) {
        this.password = newUser.password;
        this.name = newUser.name;
        this.email = newUser.email;
    }

    public boolean matchPassword(String password) {
        return this.password.equals(password);
    }

    public boolean matchPId(long pId) {
        return this.pId == pId;
    }
}
