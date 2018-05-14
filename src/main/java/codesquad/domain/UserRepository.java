package codesquad.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
//	User findByUserId(String userId);
	Optional<User> findByUserId(String userId);
}
