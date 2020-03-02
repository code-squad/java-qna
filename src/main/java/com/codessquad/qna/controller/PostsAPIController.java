package com.codessquad.qna.controller;

import com.codessquad.qna.service.posts.PostsService;
import com.codessquad.qna.web.dto.PostsSaveRequestDto;
import com.codessquad.qna.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PostsAPIController {
  private final PostsService postsService;

  @PostMapping("/api/v1/posts")
  public Long save(@RequestBody PostsSaveRequestDto requestDto) {
    return postsService.save(requestDto);
  }

  @PutMapping("/api/v1/posts/{Id}")
  public Long update(@PathVariable Long Id, @RequestBody PostsUpdateRequestDto requestDto) {
    return postsService.update(Id, requestDto);
  }
}
