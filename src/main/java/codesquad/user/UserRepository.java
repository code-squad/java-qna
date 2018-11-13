package codesquad.user;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    public static final UserRepository INSTANCE = new UserRepository();
    private List<User> users;

    private UserRepository() {
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
