package codesquad.user;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {        //클래스명, id 타입
    Optional<User> findByUserId(String userId);
}
