package codesquad.web.model;

import java.util.ArrayList;
import java.util.List;

public class Users {

    private List<User> users = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
    }

    public List<User> getUsers() {
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
}
