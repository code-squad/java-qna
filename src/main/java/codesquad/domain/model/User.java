package codesquad.domain.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue
    private Long id; // Long -> null 체크를 가능하게 함으로써 유저정보 유무를 확인

    @Column(nullable = false, length = 20, unique = true)
    private String userId;
    private String password;
    private String name;
    private String email;

    @Builder
    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public boolean update(String beforePassword, User updateUser) {
        if (!this.isMatch(beforePassword)) return false;
        if (nothingToChange(updateUser)) return false;
        this.updateUserInfo(updateUser);
        return true;
    }

    private boolean nothingToChange(User updateUser) {
        return password.equals(updateUser.password)
                && name.equals(updateUser.name)
                && email.equals(updateUser.email);
    }

    public boolean isMatch(String beforePassword) {
        return password.equals(beforePassword);
    }

    private void updateUserInfo(User updateUser) {
        password = updateUser.password;
        name = updateUser.name;
        email = updateUser.email;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(userId, user.userId) &&
                Objects.equals(password, user.password) &&
                Objects.equals(name, user.name) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userId, password, name, email);
    }
}
