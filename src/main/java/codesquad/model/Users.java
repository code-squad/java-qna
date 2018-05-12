package codesquad.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Users {
    private ArrayList<User> users = new ArrayList<>();

    public Users() {

    }

    public void add(User user) {
        user.setId(users.size() + 1);
        users.add(user);
    }

    public List<User> getUsers() {
        return users;
    }

    public Optional<User> findById(int id) {
        return users.stream().filter(user -> user.isSame(id)).findFirst();
    }
}
