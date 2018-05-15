package codesquad.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {
    private int id;
    private String userId;
    private String passwd;
    private String name;
    private String email;

    public boolean isSame(int id) {
        return this.id == id;
    }

    public void changeInfo(String currentPasswd, String changePasswd, String name, String email) throws IllegalArgumentException {
        if (!passwd.equals(currentPasswd)) {
            throw new IllegalArgumentException("현재 비밀번호가 올바르지않습니다.");
        }
        passwd = changePasswd;
        this.name = name;
        this.email = email;
    }
}
