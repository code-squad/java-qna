package codesquad;

public class User {
    private String userId;
    private String password;
    private String name;
    private String email;

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
}
