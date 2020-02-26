package com.codessquad.qna.service.posts;

import com.codessquad.qna.controller.PostsRepository;
import com.codessquad.qna.web.dto.PostsListResponseDto;
import com.codessquad.qna.web.dto.PostsSaveRequestDto;
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
        .map(PostsListResponseDto::new)
        .collect(Collectors.toList());
  }

  @Transactional
  public Long save(PostsSaveRequestDto requestDto) {
    return postsRepository.save(requestDto.toEntity()).getId();
  }
}
