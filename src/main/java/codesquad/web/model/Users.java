package codesquad.web.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Users {

    private Set<User> users = new HashSet<>();

    public void addUser(User user) {
        users.add(user);
    }

    public Set<User> getUsers() {
        return users;
    }

    public User findUser(String userId) {
        for (User user : users) {
            if (user.haveId(userId)) {
                return user;
            }
        }
        return null;
    }

    public int getSize() {
        return users.size();
    }

    public void updateUser(User updatedUser) {
        for (User user : users) {
            if (user.matchWith(updatedUser)) {
                user.update(updatedUser);
            }
        }
    }

    public User matchUser(String userId, String beforePassword) {
        User user = findUser(userId);
        if (user.matchWith(beforePassword)) return user;
        return null;
    }
}
