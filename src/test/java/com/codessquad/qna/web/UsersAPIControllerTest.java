package com.codessquad.qna.web;

import static org.assertj.core.api.Assertions.assertThat;

import com.codessquad.qna.controller.UsersRepository;
import com.codessquad.qna.domain.Users;
import com.codessquad.qna.web.dto.UsersRegisterRequestDto;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsersAPIControllerTest {

  //오토와이어링은 스프링이 빈의 요구 사항과 매칭 되는 애플리케이션 컨텍스트상에서 다른 빈을 찾아 빈 간의 의존성을 자동으로 만족시키도록 하는 수단이다.
  //오토와이어링 수행을 하도록 지정하기 위해서는 다음 스프링의 @Autowired 애너테이션을 사용한다.
  //참고로 스프링 빈은 그냥 자바 객체라고 생각해도 괜찮다고 한다.
  @Autowired
  private TestRestTemplate testRestTemplate;

  @LocalServerPort
  private int port;

  @Autowired
  private UsersRepository usersRepository;

  @Test
  public void Users가_등록된다() {

    Users testUser = Users.builder() //이게 Users가 아니고 UsersRegisterRequestDto여도 작동한다. 왜지? Type이 다를텐데?
        .userId("jypthemiracle")
        .name("Jin Hyung Park")
        .password("codesquad")
        .email("hophfg@yahoo.co.kr")
        .build();

    String url = "http://localhost:" + port + "api/v1/users";
    ResponseEntity<Long> responseEntity = testRestTemplate.postForEntity(url, testUser, Long.class);

    //test
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    List<Users> all = usersRepository.findAllDesc();
    assertThat(all.get(0).getName()).isEqualTo("Jin Hyung Park");
  }
}