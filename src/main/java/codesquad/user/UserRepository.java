package codesquad.user;

import org.springframework.data.repository.CrudRepository;

// <객체, 프라이머리키의 타입>
public interface UserRepository extends CrudRepository<User, Long> {
}
