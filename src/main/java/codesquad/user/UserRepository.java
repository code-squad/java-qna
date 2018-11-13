package codesquad.user;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private static UserRepository userRepository = new UserRepository();
    private static List<User> users = new ArrayList<>();

    private UserRepository() { }

    public static UserRepository getUserRepository() {
        if(userRepository == null) {
            userRepository = new UserRepository();
        }
        return userRepository;
    }

    static void addUser(User user) {
        users.add(user);
    }

    public static List<User> getUsers() {
        return users;
    }
}

