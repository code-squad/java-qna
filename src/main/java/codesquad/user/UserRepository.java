package codesquad.user;

import java.util.*;

public class UserRepository {
    private static UserRepository ourInstance = new UserRepository();
    private Map<String, User> map = new HashMap<>();

    private UserRepository(){}

    public static UserRepository getInstance() {
        return ourInstance;
    }

    public void add(User user) {
        map.put(user.getUserId(), user);
    }

    List<User> getAll() {
        return new ArrayList<>(map.values());
    }

    public Optional<User> findUser(String userId) {
        return Optional.ofNullable(map.get(userId));
    }

    public void update(User updateUserInfo) {
        String id = updateUserInfo.getUserId();
        if (updateUserInfo.checkPassword(map.get(id))) {
            map.put(id, updateUserInfo);
        }
    }
}
