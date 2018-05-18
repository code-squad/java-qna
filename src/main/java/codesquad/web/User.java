package codesquad.web;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    private String userId;
    private String password;
    private String name;
    private String email;

    public boolean match(String userId) {
        return this.userId.equals(userId);
    }

    public boolean matchId(Long id) {
        return this.id == id;
    }

    public boolean matchPassword(String password){
        return this.password.equals(password);
    }

    public void updateUser(User updateUser, String checkPassword) {
        if (password.equals(checkPassword)) {
            password = updateUser.password;
            name = updateUser.name;
            email = updateUser.email;
        }
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
