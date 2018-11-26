package codesquad.user;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class User {
    public static final String SESSION_NAME = "loginUser";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 15)
    private String userId;
    @Column(length = 15)
    private String password;
    @Column(length = 15)
    private String name;
    @Column(length = 25)
    private String email;

    public User() {
    }

    public User(String userId, String password, String name, String email) {
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

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public boolean checkPassword(String password) {
        if (this.password == null) {
            return false;
        }
        return this.password.equals(password);
    }

    public boolean checkPassword(User user) {
        if (this.password == null) {
            return false;
        }
        return this.password.equals(user.password);
    }

    //TODO : refactor
    public void fillEmpty(User loginUser) {
        if (this.id == null) {
            this.id = loginUser.id;
        }
        if (this.userId == null) {
            this.userId = loginUser.userId;
        }
        if (this.email == null) {
            this.email = loginUser.email;
        }
        if (this.name == null) {
            this.name = loginUser.name;
        }
        if (this.password == null) {
            this.password = loginUser.password;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, password, name, email);
    }

    public void updateProcess(User updateUserInfo) {
        this.name = updateUserInfo.name;
        this.email = updateUserInfo.email;
    }
}
