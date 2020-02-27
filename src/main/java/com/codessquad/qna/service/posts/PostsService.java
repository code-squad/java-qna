package com.codessquad.qna.service.posts;

import com.codessquad.qna.controller.PostsRepository;
import com.codessquad.qna.domain.Posts;
import com.codessquad.qna.web.dto.PostsListResponseDto;
import com.codessquad.qna.web.dto.PostsResponseDto;
import com.codessquad.qna.web.dto.PostsSaveRequestDto;
import com.codessquad.qna.web.dto.PostsUpdateRequestDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {

  private final PostsRepository postsRepository;

  @Transactional(readOnly = true)
  public List<PostsListResponseDto> findAllDesc() {
    return postsRepository.findAllDesc().stream()
        .map(posts -> new PostsListResponseDto(posts))
        .collect(Collectors.toList());
  }

  @Transactional
  public Long save(PostsSaveRequestDto requestDto) {
    return postsRepository.save(requestDto.toEntity()).getId();
  }

  @Transactional
  public Long update(Long id, PostsUpdateRequestDto requestDto) {
    Posts posts = postsRepository.findById(id).orElseThrow(
        () -> new IllegalArgumentException("no such post." + " id = " + id));
    posts.update(requestDto.getTitle(), requestDto.getContent());
    return id;
  }

  @Transactional(readOnly = true)
  public PostsResponseDto findById(Long id) {
    Posts entity = postsRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("no such post" + " id = " + id)
        );
    return new PostsResponseDto(entity);
  }
}
