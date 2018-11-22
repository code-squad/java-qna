package codesquad.user;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {
    User user1;
    User user2;

    @Before
    public void setUp() throws Exception {
        user1 = new User(new Long(25), "leeh903", "1234", "이정현", "leejh903@naver.com");
        user2 = new User(new Long(27), "brad903", "1234", "브래드", "brad903@naver.com");
    }

    @Test
    public void 사용자정보_업데이트() {
        user1.update(user2);
        assertThat(user1.getEmail()).isEqualTo(user2.getEmail());
        assertThat(user1.getName()).isEqualTo(user2.getName());
        assertThat(user1.getId()).isEqualTo(user1.getId());
    }

    @Test
    public void matchId() {
        assertThat(user1.matchId(new Long(25))).isEqualTo(true);
    }
}