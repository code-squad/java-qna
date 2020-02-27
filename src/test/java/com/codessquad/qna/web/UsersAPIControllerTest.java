package com.codessquad.qna.web;

import static org.assertj.core.api.Assertions.assertThat;

import com.codessquad.qna.controller.PostsRepository;
import com.codessquad.qna.controller.UsersRepository;
import com.codessquad.qna.domain.Posts;
import com.codessquad.qna.domain.Users;
import com.codessquad.qna.web.dto.UsersUpdateRequestDto;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
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

  @Autowired
  private PostsRepository postsRepository;

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

  @Test
  public void Users가_조회된다() {
    //when
    String title = "this is test title";
    String content = "this is test content";
    String author = "this is test author";
    //getForObject: Retrieve a representation by doing a GET on the URI template.
    postsRepository.save(Posts.builder()
        .title(title)
        .content(content)
        .author(author)
        .build()
    );
    String body = this.testRestTemplate.getForObject("/api/v1/users/list", String.class);
    //getForObject: Retrieve a representation by doing a GET on the URI template.
    //then
    assertThat(body).contains(title);
    //TestRestTemplate으로 RestTemplate를 Test해보았을 때, 넘겨주는 body 문자열에 우리가 기대하는 값이 있으면 된다.
  }

  @Test
  public void Users가_수정된다() {
    Long thisId = (long) 1;
    //given
    Users testUser = usersRepository.save(Users.builder()
        .userId("jypthemiracle")
        .password("codesquad")
        .name("Jin Hyung Park")
        .email("hophfg@yahoo.co.kr")
        .build()
    );

    String newId = "honux77";
    String newPassword = "ilovecodesquad";
    String newName = "Honux";
    String newEmail = "yodacodd@codesquad.kr";

    UsersUpdateRequestDto requestDto = UsersUpdateRequestDto.builder()
        .userId(newId)
        .password(newPassword)
        .name(newName)
        .email(newEmail)
        .build();

    String url = "http://localhost:" + port + "/api/v1/users" + thisId;
    HttpEntity<UsersUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);
    ResponseEntity<Long> responseEntity = testRestTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    List<Users> all = usersRepository.findAllDesc();
    assertThat(all.get(0).getName()).isEqualTo(newName);
  }
}