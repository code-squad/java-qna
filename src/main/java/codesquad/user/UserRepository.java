package codesquad.user;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    // findBy로 시작하는 것이 컨벤션. JPA가 해주는 것.
    Optional<User> findByUserId(String userId);
}
