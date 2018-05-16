package codesquad.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 15, unique = true, updatable = false)
    private String userId;

    @Column(nullable = false, length = 15)
    private String passwd;

    @Column(nullable = false, length = 12)
    private String name;

    @Column(nullable = false, length = 20, unique = true)
    private String email;

    public void changeInfo(String currentPasswd, User userInfo) {
        if (!passwd.equals(currentPasswd)) {
            throw new IllegalArgumentException("현재 비밀번호가 아닙니다.");
        }
        this.passwd = userInfo.passwd;
        this.name = userInfo.name;
        this.email = userInfo.email;
    }
}
