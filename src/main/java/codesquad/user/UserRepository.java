package codesquad.user;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private static UserRepository repository;
    private List<User> users = new ArrayList<>();

    private UserRepository() {

    }

    public static UserRepository getInstance() {
        if (repository == null) {
            repository = new UserRepository();
        }
        return repository;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public List<User> getUsers() {
        return users;
    }


}
