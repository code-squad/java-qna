package codesquad.controller;

import java.util.ArrayList;
import java.util.List;

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
}
