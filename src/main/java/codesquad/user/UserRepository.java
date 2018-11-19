package codesquad.user;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    //컨벤션 따라 findBy로 메서드 명명한다. UserId는 User에 있어야함!
    Optional<User> findByUserId(String userId);

}