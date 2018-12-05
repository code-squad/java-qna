package codesquad.user;

import codesquad.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity         //데이터 베이스 맵핑할때 알아서 꺼내줌
public class User extends AbstractEntity {
    @Column(nullable = false, length = 20, unique = true)
    @JsonProperty
    private String userId;

    @Column(nullable = false, length = 20)
    @JsonIgnore
    private String password;

    @Column(nullable = false, length = 20)
    @JsonProperty
    private String name;

    @Column(nullable = false, length = 20)
    @JsonProperty
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

    public void update(User updateUser) {
        this.password = updateUser.password;
        this.name = updateUser.name;
        this.email = updateUser.email;
    }

    public boolean matchPassword(String newPassword) {
        if (newPassword == null) {
            return false;
        }
        return this.password.equals(newPassword);
    }

    public boolean isMatchId(Long id) {
        return this.getId() == id;
    }

    public boolean matchUser(User sessionedUser) {
        return this.getId() == sessionedUser.getId();
    }

    @Override
    public String toString() {
        return "User{" +
                super.toString() + '\'' +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
