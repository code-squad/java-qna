package com.codessquad.qna.domain.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserDomainTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void 유저_생성_후_저장_테스트() {
        User newUser = new User("cat", "cat123", "고양이", "cat123@codesquad.kr");
        newUser = userRepository.save(newUser);
        User repositoryUser = userRepository.findByUserId("cat").orElseGet(User::new);
        assertThat(repositoryUser).isEqualTo(newUser);
    }

    @Test
    void 유저_목록_가져오기_테스트() {
        List<User> testUsers = new ArrayList<>();
        User dion = new User("dion", "dion123", "디온", "dion@codesquad.kr");
        User codesquad = new User("codesquad", "db1004", "코드스쿼드", "master@codesquad.kr");
        testUsers.add(dion);
        testUsers.add(codesquad);

        List<User> users = userRepository.findAll();
        int usersCount = users.size();
        assertThat(usersCount).isEqualTo(testUsers.size());
        assertThat(users).isEqualTo(testUsers);
    }

    @Test
    void 유저를_잘_찾아오는지_테스트() {
        User repositoryUser = userRepository.findByUserId("dion").orElseGet(User::new);
        User notExistingUser = userRepository.findByUserId("cat").orElseGet(User::new);

        assertThat(repositoryUser.getUserId()).isEqualTo("dion");
        assertThat(notExistingUser.getUserId()).isNull();
    }

    @Test
    void 유저가_같은지_테스트() {
        User dion = new User("dion", "dion123", "디온", "dion@codesquad.kr");
        User repositoryUser = userRepository.findByUserId("dion").orElseGet(User::new);
        assertThat(dion).isEqualTo(repositoryUser);
    }

    @Test
    void 비밀번호_확인_테스트() {
        User repositoryUser = userRepository.findByUserId("dion").orElseGet(User::new);
        User temporaryUserForSetPassword = new User();
        User temporaryUserForSetWrongPassword = new User();

        temporaryUserForSetPassword.setUserPassword("dion123");
        temporaryUserForSetWrongPassword.setUserPassword("asdf");

        assertThat(repositoryUser.isUserPasswordNotEquals(temporaryUserForSetPassword)).isFalse();
        assertThat(repositoryUser.isUserPasswordNotEquals(temporaryUserForSetWrongPassword)).isTrue();
    }

    @Test
    void 유저정보_업데이트_되었는지_테스트() {
        User updateUser = new User("dion", "dion123", "띠옹", "ddiong@codesquad.kr", "nonono");
        String newPassword = "dion12345";

        User dion = userRepository.findByUserId("dion").orElseGet(User::new);
        dion.update(updateUser, newPassword);
        dion = userRepository.save(dion);

        assertThat(dion.getUserId()).isEqualTo(updateUser.getUserId());
        assertThat(dion.getUserPassword()).isEqualTo(newPassword);
        assertThat(dion.getUserName()).isEqualTo(updateUser.getUserName());
        assertThat(dion.getUserEmail()).isEqualTo(updateUser.getUserEmail());
        assertThat(dion.getUserProfileImage()).isEqualTo(updateUser.getUserProfileImage());
        assertThat(dion.equals(updateUser)).isTrue();
    }

}
