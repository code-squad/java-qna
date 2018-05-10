package codesquad.controller;

import java.util.ArrayList;

public class Users {
    private ArrayList<User> users = new ArrayList<>();

    public Users() {

    }

    public void addUser(User user) {
        users.add(user);
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}
