package codesquad.user;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    // id말고 다른 값으로 select하기위해, 이름 Convection있으니 잘 지을 것
    // findBy가 고정적
        Optional<User> findByUserId(String userId);
}
