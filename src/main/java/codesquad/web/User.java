package codesquad.web;

public class User {
    private String userId;
    private String password;
    private String name;
    private String email;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return userId + " " + password + " " + name + " " + email;
    }

    public boolean isValidUserId(String userId) {
        return this.userId.equals(userId);
    }
}
