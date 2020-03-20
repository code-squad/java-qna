package com.codessquad.qna.controller.posts;

import com.codessquad.qna.domain.Posts;
import com.codessquad.qna.service.posts.PostsService;
import com.codessquad.qna.web.dto.posts.PostsDeleteRequestDto;
import com.codessquad.qna.web.dto.posts.PostsSaveRequestDto;
import com.codessquad.qna.web.dto.posts.PostsUpdateRequestDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostsAPIController {
  private final PostsService postsService;

  public PostsAPIController(PostsService postsService) {
    this.postsService = postsService;
  }

  @PostMapping("/api/v1/posts")
  public Posts save(@RequestBody PostsSaveRequestDto requestDto) {
    return postsService.save(requestDto);
  }

  @PutMapping("/api/v1/posts/{Id}")
  public Posts update(@PathVariable Long Id, @RequestBody PostsUpdateRequestDto requestDto) {
    return postsService.update(Id, requestDto);
  }

  @PutMapping("/api/v1/posts/delete/{Id}")
  public Posts delete(@PathVariable Long Id, @RequestBody PostsDeleteRequestDto requestDto) {
    return postsService.delete(Id, requestDto);
  }
}
