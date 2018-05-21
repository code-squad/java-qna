package codesquad.domain.repository;

import codesquad.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findById(Long id);
    Optional<User> findByUserId(String userId);

}
