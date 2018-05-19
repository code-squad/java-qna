package codesquad.web.domain.repository;

import codesquad.web.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findById(Long id);
    Optional<User> findByUserId(String userId);

}
