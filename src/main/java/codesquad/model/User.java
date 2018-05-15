package codesquad.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, length = 32)
    private String userId;
    @Column(nullable = false, length = 32)
    private String password;
    @Column(nullable = false, length = 64)
    private String name;
    @Column(nullable = false)
    private String email;

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userID) {
        this.userId = userID;
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

    public boolean isMatch(String userId) {
        return this.userId.equals(userId);
    }

    private boolean pwMatch(String pw) {
        return this.password.equals(pw);
    }

    public void updateUserInfo(String oldPw, String newPw, String newName, String newEmail) throws IllegalArgumentException {
        if (!pwMatch(oldPw)) {
            throw new IllegalArgumentException("사용자 정보수정 실패: 비밀번호 불일치");
        }
        this.password = newPw;
        this.name = newName;
        this.email = newEmail;
    }
}
