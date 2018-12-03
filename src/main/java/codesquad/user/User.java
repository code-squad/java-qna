package codesquad.user;

import codesquad.AbstractEntity;
import javax.persistence.*;

@Entity
public class User extends AbstractEntity {
    @Column(nullable=false, length=20, unique=true)
    private String userId;

    @Column(nullable=false, length=12)
    private String password;

    @Column(nullable=false, length=12)
    private String name;

    @Column(nullable=false, length=50)
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

    void update(User updatedUser) {
        this.setName(updatedUser.getName());
        this.setPassword(updatedUser.getPassword());
        this.setEmail(updatedUser.getEmail());
    }

    boolean matchId(long id) {
        return this.getId() == id;
    }

    public boolean matchUserId(String userId) {
        return this.userId.equals(userId);
    }

    boolean matchPassword(String password) {
        return this.password.equals(password);
    }

    @Override
    public String toString() {
        return "User{" +
                super.toString() +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
