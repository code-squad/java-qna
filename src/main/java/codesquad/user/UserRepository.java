package codesquad.user;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    // Long은 id의 타입, JDBC 사용시보다 10배 줄인 코드, 여기에 많은 뜻이 함축된 만큼 나중에 유연하게 사용하기 위해 학습비용 발생
}
