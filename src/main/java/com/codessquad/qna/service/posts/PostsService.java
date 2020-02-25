package com.codessquad.qna.service.posts;

import com.codessquad.qna.controller.PostsRepository;
import com.codessquad.qna.web.dto.PostsListResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {
  private final PostsRepository postsRepository;
  @Transactional(readOnly = true)
  public List<PostsListResponseDto> findAllDesc() {
    //input your code here
  }
}
