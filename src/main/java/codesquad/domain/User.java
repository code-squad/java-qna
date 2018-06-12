package codesquad.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class User extends AbstractEntity{
    @Column(nullable = false, length = 20, unique = true)
    @JsonProperty
    private String userId;

    @JsonIgnore
    private String password;

    @JsonProperty
    private String name;

    @JsonProperty
    private String email;

    public boolean matchId(Long newId) {
        if (newId == null) {
            return false;
        }
        return newId.equals(getId());
    }

    public boolean matchPassword(String newPassword) {
        if (newPassword == null) {
            return false;
        }

        return newPassword.equals(password);
    }

    public boolean matchUser(String userId){
        return this.userId.equals(userId);
    }

    public void updateInformation(User updater, String passwordCheck) {
        if(this.password.equals(passwordCheck)){
            name = updater.name;
            email = updater.email;
        }
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
        return "User{" + super.toString() +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
