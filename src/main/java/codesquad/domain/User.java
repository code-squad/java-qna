package codesquad.domain;

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

    @Size(min = 5, max = 15)
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

    public void changeInfo(String currentPasswd, User userInfo) {
        if (!passwd.equals(currentPasswd)) {
            throw new IllegalArgumentException("현재 비밀번호가 아닙니다.");
        }
        this.passwd = userInfo.passwd;
        this.name = userInfo.name;
        this.email = userInfo.email;
    }
}
