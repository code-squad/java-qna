//package com.codessquad.qna.web;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import com.codessquad.qna.controller.posts.PostsRepository;
//import com.codessquad.qna.domain.Posts;
//import com.codessquad.qna.web.dto.posts.PostsSaveRequestDto;
//import com.codessquad.qna.web.dto.posts.PostsUpdateRequestDto;
//import java.util.List;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.web.server.LocalServerPort;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.junit4.SpringRunner;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class PostsAPIControllerTest {
//
//  //오토와이어링은 스프링이 빈의 요구 사항과 매칭 되는 애플리케이션 컨텍스트상에서 다른 빈을 찾아 빈 간의 의존성을 자동으로 만족시키도록 하는 수단이다.
//  //오토와이어링 수행을 하도록 지정하기 위해서는 다음 스프링의 @Autowired 애너테이션을 사용한다.
//  //참고로 스프링 빈은 그냥 자바 객체라고 생각해도 괜찮다고 한다.
//  @Autowired
//  private TestRestTemplate testRestTemplate;
//
//  @LocalServerPort
//  private int port;
//
//  @Autowired
//  private PostsRepository postsRepository;
//
//  @Test
//  public void Posts가_등록된다() {
//
//    PostsSaveRequestDto testPost = PostsSaveRequestDto.builder() //Posts를 빌드하는 것이 아니라 Dto를 빌드하는 것이다.
//        .author()
//        .content("testContent")
//        .title("testTitle")
//        .build();
//
//    String url = "http://localhost:" + port + "api/v1/posts";
//    ResponseEntity<Long> responseEntity = testRestTemplate.postForEntity(url, testPost, Long.class);
//
//    //test
//    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//    List<Posts> all = postsRepository.findAllDesc();
//    assertThat(all.get(0).getContent()).isEqualTo("testContent");
//  }
//
//  @Test
//  public void Posts가_수정된다() throws Exception {
//    //given
//    Posts savedPosts = postsRepository.save(Posts.builder()
//        .title("title")
//        .content("content")
//        .author()
//        .build()
//    );
//
//    Long updateId = savedPosts.getId();
//    String expectedTitle = "title2";
//    String expectedContent = "content2";
//
//    PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
//        .title(expectedTitle)
//        .content(expectedContent)
//        .build();
//
//    String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;
//
//    HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);
//
//    //when
//    ResponseEntity<Long> responseEntity = testRestTemplate.exchange(url, HttpMethod.POST, requestEntity, Long.class);
//
//    //then
//    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//    assertThat(responseEntity.getBody()).isGreaterThan(0L);
//    List<Posts> all = postsRepository.findAllDesc();
//    assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
//    assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
//  }
//}
