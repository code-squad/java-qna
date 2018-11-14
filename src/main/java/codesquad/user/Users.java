package codesquad.user;

import java.util.ArrayList;
import java.util.List;

public class Users {
    private List<User> users = new ArrayList<>();

    private Users() {}

    public static Users of() {
        return new Users();
    }

    void add(User user) {
        users.add(user);
    }

    List<User> getUsers() {
        return users;
    }
}
