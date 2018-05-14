package codesquad.model;

import java.util.ArrayList;
import java.util.List;

public class Users {
    private List<User> users = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
    }

    public User getUser(String userId) {
        for (User user : users) {
            if (user.isMatch(userId)) {
                return user;
            }
        }
        throw new IllegalArgumentException("일치하는 사용자가 없습니다.");
    }

    public void updateUser(String userId, String oldPassword, String newPassword, String name, String email) {
        User user = getUser(userId);
        user.updateUserInfo(oldPassword, newPassword, name, email);
    }

    public List<User> getUsers() {
        return users;
    }
}
