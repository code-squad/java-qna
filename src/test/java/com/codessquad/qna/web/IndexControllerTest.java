package com.codessquad.qna.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import com.codessquad.qna.controller.PostsRepository;
import com.codessquad.qna.domain.Posts;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class IndexControllerTest {

  @Autowired
  private TestRestTemplate testRestTemplate;

  @Autowired
  private PostsRepository postsRepository;

  @Test
  public void 메인페이지가_로딩된다() {
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
    //then
    String body = this.testRestTemplate.getForObject("/", String.class);
    assertThat(body).contains(title);
    //이 테스트는 index.mustache에서 id와 createdTime이 추가될 경우 fail하게 되어 있음. 그 이유는 데이터베이스에 직접 집어넣었기 때문임.
    //TestRestTemplate으로 RestTemplate를 Test해보았을 때, 넘겨주는 body 문자열에 우리가 기대하는 값이 있으면 된다.
  }
}
