package codesquad.web.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class) // 이걸 안써주면 테스트가 안됨
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void save() {

        User user = User.builder().userId("아이디")
                .password("aaaa")
                .name("gildong")
                .email("aa@gg.com").build();

        userRepository.save(user);

        List<User> users = userRepository.findAll();

        assertThat(users.get(0).getUserId(), is("아이디"));
    }
}
