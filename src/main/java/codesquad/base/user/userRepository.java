package codesquad.base.user;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface userRepository extends CrudRepository<User, Long> {
    Optional<User> findByUserId(String userId);
}
