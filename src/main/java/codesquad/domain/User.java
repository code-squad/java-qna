package codesquad.domain;

public class User {
    private int id;
    private String userId;
    private String passwd;
    private String name;
    private String email;

    public User() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
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

    public boolean isSame(int id) {
        return this.id == id;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", passwd='" + passwd + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
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
