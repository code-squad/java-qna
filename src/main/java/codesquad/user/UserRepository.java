package codesquad.user;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    //UserDao
    //userRepository
    //인터페이스가 인터페이스를 상속할 때 는 implements 가 아닌 extends
}
