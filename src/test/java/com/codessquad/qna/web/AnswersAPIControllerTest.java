package com.codessquad.qna.web;

import static org.assertj.core.api.Assertions.assertThat;

import com.codessquad.qna.controller.answers.AnswersRepository;
import com.codessquad.qna.domain.Answers;
import com.codessquad.qna.domain.Users;
import com.codessquad.qna.web.dto.answers.AnswersSaveRequestDto;
import com.codessquad.qna.web.dto.posts.AnswersUpdateRequestDto;
import com.codessquad.qna.web.dto.posts.PostsSaveRequestDto;
import java.util.List;
import org.junit.Before;
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
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AnswersAPIControllerTest {

  @Autowired
  private TestRestTemplate testRestTemplate;

  private MockHttpSession httpSession;

  @LocalServerPort
  private int port;

  @Autowired
  private AnswersRepository answersRepository;

  @Before
  public void HttpSession을_설정한다() {
    httpSession = new MockHttpSession();
    Users testUser = new Users();
    testUser.setId(1L);
    testUser.setUserId("javajigi");
    testUser.setName("자바지기");
    testUser.setPassword("test");
    testUser.setEmail("javajigi@slipp.net");

    httpSession.setAttribute("sessionUser", testUser);
  }

  @Test
  public void Answers가_등록된다() {
    PostsSaveRequestDto testPost = PostsSaveRequestDto.builder() //Posts를 빌드하는 것이 아니라 Dto를 빌드하는 것이다.
        .author(httpSession)
        .content("testContent")
        .title("testTitle")
        .build();

    String posturl = "http://localhost:" + port + "api/v1/posts";
    ResponseEntity<Long> postResponseEntity = testRestTemplate.postForEntity(posturl, testPost, Long.class);

    AnswersSaveRequestDto testAnswers = AnswersSaveRequestDto.builder() //Posts를 빌드하는 것이 아니라 Dto를 빌드하는 것이다.
        .title("testTitle")
        .content("testContent")
        .postId(1L)
        .author(httpSession)
        .build();

    String answerurl = "http://localhost:" + port + "api/v1/answers";
    ResponseEntity<Long> answerResponseEntity = testRestTemplate
        .postForEntity(answerurl, testAnswers, Long.class);

    //test
    assertThat(answerResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    List<Answers> all = answersRepository.findAllDesc();
    assertThat(all.get(0).getContent()).isEqualTo("testContent");
  }

  @Test
  public void Answers가_수정된다() throws Exception {
    PostsSaveRequestDto testPost = PostsSaveRequestDto.builder() //Posts를 빌드하는 것이 아니라 Dto를 빌드하는 것이다.
        .author(httpSession)
        .content("testContent")
        .title("testTitle")
        .build();

    String posturl = "http://localhost:" + port + "api/v1/posts";
    ResponseEntity<Long> postResponseEntity = testRestTemplate.postForEntity(posturl, testPost, Long.class);

    AnswersSaveRequestDto testAnswers = AnswersSaveRequestDto.builder() //Posts를 빌드하는 것이 아니라 Dto를 빌드하는 것이다.
        .title("testTitle")
        .content("testContent")
        .postId(1L)
        .author(httpSession)
        .build();

    String answerurl = "http://localhost:" + port + "api/v1/answers";
    ResponseEntity<Long> answerResponseEntity = testRestTemplate
        .postForEntity(answerurl, testAnswers, Long.class);

    String expectedTitle = "modified title";
    String expectedContent = "modified content";

    AnswersUpdateRequestDto requestDto = AnswersUpdateRequestDto.builder()
        .title(expectedTitle)
        .content(expectedContent)
        .build();

    String url = "http://localhost:" + port + "/api/v1/answers/" + 1;

    HttpEntity<AnswersUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

    ResponseEntity<Long> responseEntity = testRestTemplate
        .exchange(url, HttpMethod.PUT, requestEntity, Long.class);

    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseEntity.getBody()).isGreaterThan(0L);
    List<Answers> all = answersRepository.findAllDesc();
    assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
    assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
  }
}