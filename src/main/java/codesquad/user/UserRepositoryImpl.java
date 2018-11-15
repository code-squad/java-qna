package codesquad.user;

import java.util.ArrayList;
import java.util.List;

// UserDao라고 해도 된다.
public class UserRepositoryImpl {
    public static final UserRepositoryImpl INSTANCE = new UserRepositoryImpl();
    private List<User> users;

    private UserRepositoryImpl() {
        this.users = new ArrayList<>();
    }

    public void addUser(User user) { users.add(user);
    }

    public List<User> getUsers() {
        return users;
    }

    public User findUser(String userId) {
        return users.stream().filter(u -> u.isUserId(userId)).findFirst().orElseThrow(IllegalArgumentException::new);
    }

    public void modifyUser(User modifiedUser) {
        for (User user : users) {
            if (user.isUserId(modifiedUser.getUserId())) {
                user.setEmail(modifiedUser.getEmail());
                user.setPassword(modifiedUser.getPassword());
                user.setName(modifiedUser.getName());
            }
        }
    }

}
