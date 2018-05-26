package codesquad.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class User extends AbstratEntity {

    @Column(nullable = false, length = 20, unique = true)
    private String userId;
    private String password;
    private String name;
    private String email;

    public boolean match(String userId) {
        return this.userId.equals(userId);
    }

    public boolean matchPassword(String password){
        return this.password.equals(password);
    }

    public boolean isSameWriter(Question question){
        return this.equals(question.getWriter());
    }

    public boolean isSameWriterOfAnswer(Answer answer){
        return this.equals(answer.getWriter());
    }

    public void updateUser(User updateUser, String checkPassword) {
        if (password.equals(checkPassword)) {
            password = updateUser.password;
            name = updateUser.name;
            email = updateUser.email;
        }
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
                super.toString() + '\'' +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
