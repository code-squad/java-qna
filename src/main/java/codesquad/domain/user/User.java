package codesquad.domain.user;

import codesquad.domain.exception.UnAuthorizedException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, max = 15)
    @Column(nullable = false, length = 15, unique = true, updatable = false)
    private String userId;

    @Size(min = 3, max = 15)
    @Column(nullable = false, length = 15)
    @JsonIgnore
    private String passwd;

    @Column(nullable = false, length = 15)
    private String name;

    @Email
    @Column(nullable = false, length = 30)
    private String email;

    @Builder
    public User(String userId, String passwd, String name, String email) {
        this.userId = userId;
        this.passwd = passwd;
        this.name = name;
        this.email = email;
    }

    public void update(User sessionUser, String currentPasswd, User userInfo) {
        if (isMatch(sessionUser)) {
            throw new UnAuthorizedException("user.mismatch.sessionuser");
        }

        if (!isMatch(currentPasswd)) {
            throw new UnAuthorizedException("user.mismatch.password");
        }
        this.passwd = userInfo.passwd;
        this.name = userInfo.name;
        this.email = userInfo.email;
    }

    public boolean isMatch(String passwd) {
        if (passwd == null) {
            return false;
        }
        return this.passwd.equals(passwd);
    }

    public boolean isMatch(Long id) {
        if (id == null) {
            return false;
        }
        return this.id.equals(id);
    }

    public boolean isMatch(User sessionUser) {
        return this.id.equals(sessionUser.id);
    }
}
