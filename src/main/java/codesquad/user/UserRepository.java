package codesquad.user;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    // 메소드 추가 : 무조건 findBy로 메소드 이름이 시작해야 한다.
    // 실행하면  findBy는 자르고 그 뒤에 따라오는 값을 찾는다.
    Optional<User> findByUserId(String userId);

}
