package codesquad.web.model;

import java.util.Objects;

public class User {

    private String userId;
    private String password;
    private String name;
    private String email;
    private int id;

    public User() { }

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

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

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public boolean haveId(String userId) {
        return this.userId.equals(userId);
    }

    public boolean matchWith(String beforePassword) {
        return this.password.equals(beforePassword);
    }

    public boolean matchWith(User updatedUser) {
        return this.userId.equals(updatedUser.userId) && this.password.equals(updatedUser.password);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public void update(String password, String name, String email) {
        this.password = password;
        this.name = name;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) &&
                Objects.equals(password, user.password) &&
                Objects.equals(name, user.name) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userId, password, name, email);
    }

    public void setId(int id) {
        this.id = id;
    }
}
