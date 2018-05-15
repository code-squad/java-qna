package codesquad.web.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Users {

    private List<User> users = new ArrayList<>();

    public void addUser(User user) {
        user.setId(users.size());
        users.add(user);
    }

    public List<User> getUsers() {
        return users;
    }

    public User findUser(int id) {
        return users.get(id);
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

    public User matchUser(String userId, String beforePassword) {
        User user = findUser(userId);
        if (user.matchWith(beforePassword)) return user;
        return null;
    }
}
