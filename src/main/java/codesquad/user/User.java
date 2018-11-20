package codesquad.user;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동으로 1씩 증가하며 아이디를 부여.
    private long id;

    @Column(unique = true, nullable = false)
    private String userId;
    private String password;
    private String name;
    private String email;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isUser(String userId){
        return userId.equals(this.userId);
    }

    public void update(User modifiedUser) {
        this.name = modifiedUser.name;
        this.email = modifiedUser.email;
    }

    public boolean matchPassword(String password) {
        return this.password.equals(password);
    }

    public boolean matchPassword(User user) {
        return user.matchPassword(this.password);
    }

    public boolean matchId(long id){
        return this.id == id;
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
