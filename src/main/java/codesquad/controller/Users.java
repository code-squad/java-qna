package codesquad.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Users {
    private ArrayList<User> users = new ArrayList<>();

    public Users() {

    }

    public void addUser(User user) {
        users.add(user);
    }

    public List<User> getUsers() {
        return users;
    }

    public Optional<User> findById(String userId) {
        return users.stream().filter(user -> user.isSame(userId)).findFirst();
    }
}
