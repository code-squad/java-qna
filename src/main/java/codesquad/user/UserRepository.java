package codesquad.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    /*@Query("select u from User u where u.userId = ?1")
    User findByUserId(String userId);*/
    //UserDao
    //userRepository
    //인터페이스가 인터페이스를 상속할 때 는 implements 가 아닌 extends
}
