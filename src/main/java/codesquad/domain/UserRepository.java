package codesquad.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // userId를 기반으로 조회를 할 수 있다.
    User findByUserId(String userId);
}
