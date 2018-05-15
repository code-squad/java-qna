package codesquad.model;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {

    public User findUserByUserId(String userId);

    public User findUserByUserId(User user);
}
