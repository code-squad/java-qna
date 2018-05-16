package codesquad.domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 15, unique = true, updatable = false)
    private String userId;

    @Column(nullable = false, length = 15)
    private String passwd;

    @Column(nullable = false, length = 15)
    private String name;

    @Column(nullable = false, length = 30, unique = true)
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
