package codesquad.model;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {

    Optional<User> findUserByUserId(String userId);

    User getUserByUserId(String userId);
}
