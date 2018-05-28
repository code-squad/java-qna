package codesquad.domain.user;

import codesquad.domain.BaseEntity;
import codesquad.domain.exception.UnAuthorizedException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Optional;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {
    @Size(min = 3, max = 15)
    @Column(nullable = false, length = 15, unique = true, updatable = false)
    @JsonIgnore
    private String userId;

    @Size(min = 3, max = 15)
    @Column(nullable = false, length = 15)
    @JsonIgnore
    private String passwd;

    @Column(nullable = false, length = 15)
    private String name;

    @Email
    @Column(nullable = false, length = 30)
    @JsonIgnore
    private String email;

    @Builder
    public User(String userId, String passwd, String name, String email) {
        this.userId = userId;
        this.passwd = passwd;
        this.name = name;
        this.email = email;
    }

    public void update(Optional<User> maybeSessionUser, String currentPasswd, User userInfo) {
        maybeSessionUser.filter(this::equals).orElseThrow(() -> new UnAuthorizedException("user.mismatch.sessionuser"));
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
}
